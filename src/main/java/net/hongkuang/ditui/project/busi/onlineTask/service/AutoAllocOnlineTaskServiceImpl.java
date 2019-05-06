package net.hongkuang.ditui.project.busi.onlineTask.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.onlineTask.service.IAutoAllocOnlineTaskService;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.enums.OnlineTaskAllocType;
import net.hongkuang.ditui.project.busi.onlineTask.mapper.OnlineTaskMapper;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.domain.RealTimeOnlineSetting;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.mapper.RealTimeOnlineSettingMapper;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTask;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.mapper.RealTimeOnlineTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author:zy
 * @date: 2019/4/18
 */
@Service
public class AutoAllocOnlineTaskServiceImpl implements IAutoAllocOnlineTaskService {

    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private RealTimeOnlineTaskMapper realTimeOnlineTaskMapper;
    @Autowired
    private RealTimeOnlineSettingMapper realTimeOnlineSettingMapper;
    @Autowired
    private OnlineTaskMapper onlineTaskMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int automaticAssign(Long[] ids,Long teamId,String taskDate) {
        // 获取未领取任务商品
        List<TbTransactionWaitBuildGoodsDto> goodsDtoList = tbTransactionOrderMapper.getAutomaticGoodsList(TbTransactionOrderStatus.UNFINISHED.getCode(),ids);
        List<String> goodsNames = new ArrayList<>();
        for (TbTransactionWaitBuildGoodsDto tbTransactionWaitBuildGoodsDto : goodsDtoList) {
            goodsNames.add(tbTransactionWaitBuildGoodsDto.getGoodsName());
        }
        // 按照节点进行配置
        List<RealTimeOnlineSetting> realTimeOnlineSettingList = realTimeOnlineSettingMapper.selectByOnlineTaskDate(taskDate,teamId);
        if (realTimeOnlineSettingList == null || realTimeOnlineSettingList.isEmpty()) {
            throw new BusinessException(taskDate + "实时任务分配节点未配置，请进行配置");
        }

        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        int totalResult = 0;
        //待分配单量
        int waitAssigned = ids.length;
        Map<String, Integer> fullSizeCount = new HashMap<>();

        while (waitAssigned>0) {
            for (RealTimeOnlineSetting realTimeOnlineSetting : realTimeOnlineSettingList) {
                //系统已有的单量
                int addCount = realTimeOnlineTaskMapper.selectRealTimeOnlineTaskCount(realTimeOnlineSetting.getStartTimeNode(), realTimeOnlineSetting.getStopTimeNode(), realTimeOnlineSetting.getTaskDate(),teamId);
                if (fullSizeCount.size() >= realTimeOnlineSettingList.size()) {
                    waitAssigned = 0;
                    break;
                }
                if(waitAssigned<=0){
                    break;
                }
                if(realTimeOnlineSetting.getTaskNum()<=0){
                    fullSizeCount.put(realTimeOnlineSetting.getRealTimeId(), 1);
                    continue;
                }

                PageHelper.startPage(1, 1);
                List<OnlineTask> waitAllocTaskList = onlineTaskMapper.selectAllocTaskList(goodsNames, TbTransactionOrderAllocatStatus.UNFINISHED.getCode(),ids);
                if (waitAllocTaskList.isEmpty()) {
                    waitAssigned = 0;
                    break;
                }
                List<RealTimeOnlineTask> realTimeOnlineTaskList = new ArrayList<>(waitAllocTaskList.size());
                List<String> taskIdList = new ArrayList<>();
                for (OnlineTask onlineTask : waitAllocTaskList) {
                    taskIdList.add(onlineTask.getTaskId());
                    RealTimeOnlineTask realTimeOnlineTask = new RealTimeOnlineTask(realTimeOnlineSetting, onlineTask);
                    realTimeOnlineTask.setCreateBy(user);
                    realTimeOnlineTask.setUpdateBy(user);
                    realTimeOnlineTask.setCreateTime(nowDate);
                    realTimeOnlineTask.setUpdateTime(nowDate);
                    realTimeOnlineTask.setTeamId(teamId);
                    realTimeOnlineSetting.setTaskNum(realTimeOnlineSetting.getTaskNum()-1);
                    if(realTimeOnlineSetting.getTaskNum()<0){
                        break;
                    }
                    realTimeOnlineTaskList.add(realTimeOnlineTask);
                }
                // 更新任务状态为已分配
                int updateTaskResult = onlineTaskMapper.updateBatchStatusByIds(taskIdList,
                        TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                        , OnlineTaskAllocType.REAL_TIME.getCode()
                        , realTimeOnlineSetting.getStartTimeNode(), realTimeOnlineSetting.getStopTimeNode());
                tbTransactionOrderMapper.updateTbTransactionOrderAllocatStatusInTaskIds(ids, TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
                if(realTimeOnlineTaskList.size()!=0){
                    // 插入实时任务
                    int insertTaskResult = realTimeOnlineTaskMapper.batchInsert(realTimeOnlineTaskList);
                    if (updateTaskResult != insertTaskResult) {
                        throw new BusinessException("添加实时任务失败，请重试");
                    }
                    totalResult += insertTaskResult;
                }
                waitAssigned = waitAssigned-1;
            }
        }

        if (fullSizeCount.size() == realTimeOnlineSettingList.size() && totalResult == 0) {
            throw new BusinessException(DateUtils.dateTime(nowDate) + "节点实时任务已分配完毕，请知晓");
        }
        return totalResult;
    }
}
