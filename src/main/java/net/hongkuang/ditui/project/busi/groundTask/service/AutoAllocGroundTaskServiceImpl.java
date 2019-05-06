package net.hongkuang.ditui.project.busi.groundTask.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.enums.GroundTaskAllocType;
import net.hongkuang.ditui.project.busi.groundTask.mapper.GroundTaskMapper;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.mapper.RealTimeGroundSettingMapper;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.mapper.RealTimeGroundTaskMapper;
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
public class AutoAllocGroundTaskServiceImpl implements IAutoAllocGroundTaskService {

    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private RealTimeGroundTaskMapper realTimeGroundTaskMapper;
    @Autowired
    private RealTimeGroundSettingMapper realTimeGroundSettingMapper;
    @Autowired
    private GroundTaskMapper groundTaskMapper;

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
        List<RealTimeGroundSetting> realTimeGroundSettingList = realTimeGroundSettingMapper.selectByGroundTaskDate(taskDate,teamId);
        if (realTimeGroundSettingList == null || realTimeGroundSettingList.isEmpty()) {
            throw new BusinessException(taskDate + "实时任务分配节点未配置，请进行配置");
        }

        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        int totalResult = 0;
        //待分配单量
        int waitAssigned = ids.length;
        Map<String, Integer> fullSizeCount = new HashMap<>();

        while (waitAssigned>0) {
            for (RealTimeGroundSetting realTimeGroundSetting : realTimeGroundSettingList) {
                //系统已有的单量
                int addCount = realTimeGroundTaskMapper.selectRealTimeGroundTaskCount(realTimeGroundSetting.getStartTimeNode(), realTimeGroundSetting.getStopTimeNode(), realTimeGroundSetting.getTaskDate(),teamId);
                if (fullSizeCount.size() >= realTimeGroundSettingList.size()) {
                    waitAssigned = 0;
                    break;
                }
                if(waitAssigned<=0){
                    break;
                }
                if(realTimeGroundSetting.getTaskNum()<=0){
                    fullSizeCount.put(realTimeGroundSetting.getRealTimeId(), 1);
                    continue;
                }

                PageHelper.startPage(1, 1);
                List<GroundTask> waitAllocTaskList = groundTaskMapper.selectAllocTaskList(goodsNames, TbTransactionOrderAllocatStatus.UNFINISHED.getCode(),ids);
                if (waitAllocTaskList.isEmpty()) {
                    waitAssigned = 0;
                    break;
                }
                List<RealTimeGroundTask> realTimeGroundTaskList = new ArrayList<>(waitAllocTaskList.size());
                List<String> taskIdList = new ArrayList<>();
                for (GroundTask groundTask : waitAllocTaskList) {
                    taskIdList.add(groundTask.getTaskId());
                    RealTimeGroundTask realTimeGroundTask = new RealTimeGroundTask(realTimeGroundSetting, groundTask);
                    realTimeGroundTask.setCreateBy(user);
                    realTimeGroundTask.setUpdateBy(user);
                    realTimeGroundTask.setCreateTime(nowDate);
                    realTimeGroundTask.setUpdateTime(nowDate);
                    realTimeGroundTask.setTeamId(teamId);
                    realTimeGroundSetting.setTaskNum(realTimeGroundSetting.getTaskNum()-1);
                    if(realTimeGroundSetting.getTaskNum()<0){
                        break;
                    }
                    realTimeGroundTaskList.add(realTimeGroundTask);
                }
                // 更新任务状态为已分配
                int updateTaskResult = groundTaskMapper.updateBatchStatusByIds(taskIdList,
                        TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                        , GroundTaskAllocType.REAL_TIME.getCode()
                        , realTimeGroundSetting.getStartTimeNode(), realTimeGroundSetting.getStopTimeNode());
                tbTransactionOrderMapper.updateTbTransactionOrderAllocatStatusInTaskIds(ids, TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
                if(realTimeGroundTaskList.size()!=0){
                    // 插入实时任务
                    int insertTaskResult = realTimeGroundTaskMapper.batchInsert(realTimeGroundTaskList);
                    if (updateTaskResult != insertTaskResult) {
                        throw new BusinessException("添加实时任务失败，请重试");
                    }
                    totalResult += insertTaskResult;
                }
                waitAssigned = waitAssigned-1;
            }
        }

        if (fullSizeCount.size() == realTimeGroundSettingList.size() && totalResult == 0) {
            throw new BusinessException(DateUtils.dateTime(nowDate) + "节点实时任务已分配完毕，请知晓");
        }
        return totalResult;
    }
}
