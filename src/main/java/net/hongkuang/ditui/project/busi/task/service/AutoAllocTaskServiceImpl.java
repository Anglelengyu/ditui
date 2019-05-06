package net.hongkuang.ditui.project.busi.task.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.order.dto.UnfinishGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.mapper.RealTimeSettingMapper;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;
import net.hongkuang.ditui.project.busi.realTimeTask.mapper.RealTimeTaskMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.enums.TaskAllocType;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by apple on 2019/1/5.
 */
@Service
public class AutoAllocTaskServiceImpl implements IAutoAllocTaskService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RealTimeTaskMapper realTimeTaskMapper;
    @Autowired
    private RealTimeSettingMapper realTimeSettingMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int automaticAssign() {
        // 获取未领取任务商品
        List<UnfinishGoodsDto> goodsDtoList = orderMapper.getAutomaticGoodsList(OrderStatus.UNFINISHED.getCode());
        List<String> goodsNames = new ArrayList<>();
        for (UnfinishGoodsDto unfinishGoodsDto : goodsDtoList) {
            goodsNames.add(unfinishGoodsDto.getGoodsName());
        }

        String now = DateUtils.getDate();
        // 按照节点进行配置
        List<RealTimeSetting> realTimeSettingList = realTimeSettingMapper.selectByTaskDate(now);
        if (realTimeSettingList == null || realTimeSettingList.isEmpty()) {
            throw new BusinessException(now + "实时任务分配节点未配置，请进行配置");
        }

        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        int totalResult = 0;
        boolean isAlloc = true;
        Map<String, Integer> fullSizeCount = new HashMap<>();
        while (isAlloc) {
            for (RealTimeSetting realTimeSetting : realTimeSettingList) {
                int addCount = realTimeTaskMapper.selectRealTimeTaskCount(realTimeSetting.getStartTimeNode(), realTimeSetting.getStopTimeNode(), realTimeSetting.getTaskDate());
                if (addCount >= realTimeSetting.getTaskNum()) {
                    fullSizeCount.put(realTimeSetting.getRealTimeId(), 1);
                }

                if (fullSizeCount.size() >= realTimeSettingList.size()) {
                    isAlloc = false;
                    break;
                }

                int enableAllocCount = realTimeSetting.getTaskNum() - addCount;
                if (enableAllocCount > 0) {
                    PageHelper.startPage(1, 1);
                    List<Task> waitAllocTaskList = taskMapper.selectAllocTaskList(goodsNames, OrderAllocatStatus.UNFINISHED.getCode());
                    if (waitAllocTaskList.isEmpty()) {
                        isAlloc = false;
                        break;
                    }
                    List<RealTimeTask> realTimeTaskList = new ArrayList<>(waitAllocTaskList.size());
                    List<String> taskIdList = new ArrayList<>();
                    for (Task task : waitAllocTaskList) {
                        taskIdList.add(task.getTaskId());
                        RealTimeTask realTimeTask = new RealTimeTask(realTimeSetting, task);
                        realTimeTask.setCreateBy(user);
                        realTimeTask.setUpdateBy(user);
                        realTimeTask.setCreateTime(nowDate);
                        realTimeTask.setUpdateTime(nowDate);
                        realTimeTaskList.add(realTimeTask);
                    }
                    // 更新任务状态为可分配
                    int updateTaskResult = taskMapper.updateBatchStatusByIds(taskIdList,
                            OrderAllocatStatus.COMPLETE.getCode(), OrderAllocatStatus.UNFINISHED.getCode()
                            , TaskAllocType.REAL_TIME.getCode()
                            , realTimeSetting.getStartTimeNode(), realTimeSetting.getStopTimeNode());
                    // 插入实时任务
                    int insertTaskResult = realTimeTaskMapper.batchInsert(realTimeTaskList);
                    if (updateTaskResult != insertTaskResult) {
                        throw new BusinessException("添加实时任务失败，请重试");
                    }
                    totalResult += insertTaskResult;
                }
            }
        }

        if (fullSizeCount.size() == realTimeSettingList.size() && totalResult == 0) {
            throw new BusinessException(DateUtils.dateTime(nowDate) + "节点实时任务已分配完毕，请知晓");
        }
        return totalResult;
    }
}
