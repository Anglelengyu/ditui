package net.hongkuang.ditui.project.busi.onlineTask.service;

import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.mapper.EmployeeMapper;
import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.domain.OnlineEmployeeOrder;
import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.mapper.OnlineEmployeeOrderMapper;
import net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain.OnlineEmployeeTask;
import net.hongkuang.ditui.project.busi.onlineEmployeeTask.mapper.OnlineEmployeeTaskMapper;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTaskVo;
import net.hongkuang.ditui.project.busi.onlineTask.domain.SearchOnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.enums.OnlineTaskAllocType;
import net.hongkuang.ditui.project.busi.onlineTask.mapper.OnlineTaskMapper;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.mapper.OnlineTaskOrderMapper;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.domain.RealTimeOnlineSetting;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTask;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.mapper.RealTimeOnlineTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 任务 服务层实现
 *
 * @author:zy
 * @date: 2019/4/17
 */
@Service
public class OnlineTaskServiceImpl implements IOnlineTaskService {

    @Autowired
    private OnlineTaskMapper onlineTaskMapper;
    @Autowired
    private OnlineTaskOrderMapper onlineTaskOrderMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private RealTimeOnlineTaskMapper realTimeOnlineTaskMapper;
    @Autowired
    private OnlineEmployeeOrderMapper onlineEmployeeOrderMapper;
    @Autowired
    private OnlineEmployeeTaskMapper onlineEmployeeTaskMapper;
    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    @Override
    public List<OnlineTaskVo> selectTaskList(SearchOnlineTask task) {
        if (!StringUtils.isEmpty(task.getStartTime())) {
            task.setStartTime(task.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(task.getEndTime())) {
            task.setEndTime(task.getEndTime() + " 23:59:59");
        }
        return onlineTaskMapper.selectOnlineTaskList(task);
    }

    @Override
    public OnlineTask selectOnlineTaskById(Long id) {
        return onlineTaskMapper.selectOnlineTaskById(id);
    }

    @Override
    public OnlineTask selectOnlineTaskByOrderId(String orderId) {
        return onlineTaskMapper.selectOnlineTaskByOrderId(orderId);
    }

    @Override
    public int updateOnlineTaskTeamRemark(OnlineTask onlineTask) {
        return onlineTaskMapper.updateOnlineTaskTeamRemark(onlineTask);
    }

    /**
     * 删除任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteOnlineTaskByIds(String ids) {
        List<OnlineTask> taskList = onlineTaskMapper.selectOnlineTaskByIds(Convert.toStrArray(ids));
        if (taskList == null || taskList.isEmpty()) {
            throw new BusinessException("请选择删除任务");
        }
        List<String> taskIdList = new ArrayList<>(taskList.size());
        List<String> cancelTaskIdList = new ArrayList<>();
        List<String> receivedTaskIdList = new ArrayList<>();
        for (OnlineTask onlineTask : taskList) {
            taskIdList.add(onlineTask.getTaskId());
            if (TaskTaskStatus.CANCEL.getCode().equals(onlineTask.getTaskStatus())) {
                cancelTaskIdList.add(onlineTask.getTaskId());
            }
            List<String> orderIdList = onlineTaskOrderMapper.selectOnlineTaskOrderIdByTaskId(onlineTask.getTaskId());
            if (!orderIdList.isEmpty()) {
                // 删除订单
                tbTransactionOrderMapper.deleteTbTransactionOrderByOrderIds(orderIdList.toArray(new String[orderIdList.size()]));
                // 删除任务订单
                onlineTaskOrderMapper.deleteOnlineTaskOrderByOrderIdList(orderIdList);
                // TODO 删除取消订单
//                orderCancelApprovalMapper.deleteOrderCancelApprovalByOrderIdList(orderIdList);
            }
            if (onlineTask.getTaskStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                receivedTaskIdList.add(onlineTask.getTaskId());
                if (!orderIdList.isEmpty()) {
                    //TODO 删除业务员订单
//                    saleOrderMapper.deleteSaleOrderByOrderIdList(orderIdList);
                }
            }
        }
        if (!cancelTaskIdList.isEmpty()) {
            //TODO 删除取消任务
//            taskCancelApprovalMapper.deleteTaskCancelApprovalByTaskIdList(taskIdList);
        }
        if (!receivedTaskIdList.isEmpty()) {
            //TODO 删除业务员任务表
//            saleTaskMapper.deleteByTaskIds(receivedTaskIdList.toArray(new String[receivedTaskIdList.size()]));
        }
        return onlineTaskMapper.deleteOnlineTaskByIds(Convert.toStrArray(ids));
    }

    @Override
    public int updateBatchOnlineTaskStatusAllot(List<String> taskIds) {
        return onlineTaskMapper.updateBatchOnlineTaskStatusAllot(taskIds);
    }

    @Override
    @Transactional
    public int toReal(String taskDate, String startTime, String stopTime, String taskIdStr,Long teamId) {
        List<String> taskIds = Arrays.asList(taskIdStr.split(","));
        List<RealTimeOnlineTask> realTimeOnlineTaskList = new ArrayList<>(taskIds.size());
        RealTimeOnlineSetting realTimeOnlineSetting = new RealTimeOnlineSetting();
        realTimeOnlineSetting.setStartTimeNode(startTime);
        realTimeOnlineSetting.setStopTimeNode(stopTime);
        realTimeOnlineSetting.setTaskDate(taskDate);
//        List<String> taskIdList = new ArrayList<>();
        AtomicReference<OnlineTask> task = new AtomicReference<>();
        AtomicReference<RealTimeOnlineTask> realTimeTask = new AtomicReference<>();
        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        taskIds.forEach(taskId -> {
            task.set(new OnlineTask());
            task.get().setTaskId(taskId);
            realTimeTask.set(new RealTimeOnlineTask(realTimeOnlineSetting, task.get()));
            realTimeTask.get().setCreateBy(user);
            realTimeTask.get().setUpdateBy(user);
            realTimeTask.get().setCreateTime(nowDate);
            realTimeTask.get().setUpdateTime(nowDate);
            realTimeTask.get().setTeamId(teamId);
            realTimeTask.get().setOrLock(0);
            realTimeOnlineTaskList.add(realTimeTask.get());
        });
        // 更新任务状态为可分配
        int updateTaskResult = onlineTaskMapper.updateBatchStatusByIds(taskIds,
                TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                , OnlineTaskAllocType.REAL_TIME.getCode()
                , realTimeOnlineSetting.getStartTimeNode(), realTimeOnlineSetting.getStopTimeNode());
        // 插入实时任务
        int insertTaskResult = realTimeOnlineTaskMapper.batchInsert(realTimeOnlineTaskList);
        if (updateTaskResult != insertTaskResult) {
            throw new BusinessException("添加实时任务失败，请重试");
        }
        return 1;
    }

    @Override
    @Transactional
    public int toSale(String[] taskIds, String employeeId, String userId,Long teamId) {
        JSONObject checkResult = (JSONObject) JSONObject.toJSON(checkStatus(userId));
        Integer status = checkResult.getInteger("status");
        switch (status) {
            case 2:
                throw new BusinessException("该业务员有正在申请取消的任务,请重新选择业务员");
            case 3:
                throw new BusinessException("该业务员有正在申请取消的订单,请重新选择业务员");
            case 1:
                throw new BusinessException("该业务员有未完成的任务,请重新选择业务员");
            case 0:
                Date date = DateUtils.getNowDate();
                List<OnlineEmployeeOrder> onlineEmployeeOrderList = new ArrayList<>();
                List<String> updateOrderIdList = new ArrayList<>();
                List<OnlineEmployeeTask> onlineEmployeeTaskList = new ArrayList<>();
                // 获取关联订单
                List<String> orderIdList = onlineTaskOrderMapper.selectOnlineTaskOrderIdByTaskIds(taskIds);
                for (String orderId : orderIdList) {
                    OnlineEmployeeOrder onlineEmployeeOrder = OnlineEmployeeOrder.build(orderId, employeeId);
                    onlineEmployeeOrder.setCreateTime(date);
                    onlineEmployeeOrder.setUpdateTime(date);
                    onlineEmployeeOrder.setTeamId(teamId);
                    onlineEmployeeOrderList.add(onlineEmployeeOrder);
                }
                updateOrderIdList.addAll(orderIdList);
                // 修改任务状态

                onlineTaskMapper.updateBatchTaskStatusByIds(taskIds, TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), employeeId);
                onlineTaskMapper.updateAllTaskStatus(TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), taskIds);
                for(String taskId :taskIds){
                    OnlineEmployeeTask onlineEmployeeTask = OnlineEmployeeTask.build(taskId, employeeId);
                    onlineEmployeeTask.setCreateTime(date);
                    onlineEmployeeTask.setUpdateTime(date);
                    onlineEmployeeTask.setTeamId(teamId);
                    onlineEmployeeTaskList.add(onlineEmployeeTask);
                }
                onlineEmployeeTaskMapper.batchInsert(onlineEmployeeTaskList);
                // 创建业务员订单表
                onlineEmployeeOrderMapper.batchInsert(onlineEmployeeOrderList);
                // 修改订单状态
                tbTransactionOrderMapper.updateTbTransactionOrderStatusInOrderIds(updateOrderIdList, TbTransactionOrderStatus.COMPLETE.getCode(), TbTransactionOrderStatus.UNFINISHED.getCode());

                tbTransactionOrderMapper.updateTbTransactionOrderAllocatStatusInOrderIds(updateOrderIdList, TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
                return 1;
        }
        return 0;
    }

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中 0  正常
     */
    @Override
    public Map<String, Object> checkStatus(String userId) {
        Employee employee = employeeMapper.selectEmployeeByUserId(Long.valueOf(userId));
        Map<String, Object> result = new HashMap<>();
        // 获取当前是否有任务正在进行中
        OnlineTask canceling = onlineTaskMapper.checkOnlineTaskStatusByEmployeeId(employee.getEmployeeId(), TaskTaskStatus.CANCEL.getCode());
        if (canceling != null) {
            result.put("status", 2);
            result.put("taskId", canceling.getTaskId());
            return result;
        }
        TbTransactionOrder orderCancaling = tbTransactionOrderMapper.checkTbTransactionOrderStatusByEmployeeId(employee.getEmployeeId(), TbTransactionOrderStatus.PLACEMENT.getCode());
        if (orderCancaling != null) {
            result.put("status", 3);
            result.put("taskId", orderCancaling.getTaskId());
            return result;
        }
        OnlineTask running = onlineTaskMapper.checkOnlineTaskStatusByEmployeeId(employee.getEmployeeId(), TaskTaskStatus.RECEIVED.getCode());

        if (running != null) {
            result.put("status", 1);
            result.put("taskId", running.getTaskId());
            return result;
        }

        result.put("status", 0);
        return result;
    }

    @Override
    public boolean selectOnlineTaskIsDistribution(String[] ids) {
        List<TbTransactionOrder> tbTransactionOrderList = tbTransactionOrderMapper.selectTbTransactionOrderByTaskIds(ids);
        boolean flag = true;
        for(TbTransactionOrder tbTransactionOrder : tbTransactionOrderList){
            if (tbTransactionOrder.getReceiptTeamId() != null && tbTransactionOrder.getReceiptTeamId() != 0) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int taskDistributionTeam(String[] taskIds, Long teamId, String teamName) {
        List<TbTransactionOrder> tbTransactionOrderList = tbTransactionOrderMapper.selectTbTransactionOrderByTaskIds(taskIds);
        boolean flag = true;
        List<Long> ids = new ArrayList<>();
        for(TbTransactionOrder tbTransactionOrder : tbTransactionOrderList){
            if (tbTransactionOrder.getReceiptTeamId() != null && tbTransactionOrder.getReceiptTeamId() != 0) {
                flag = false;
            }
            ids.add(tbTransactionOrder.getId());
        }
        if(!flag){
            return 0;
        }
        return tbTransactionOrderMapper.taskDistributionTeam(ids,teamId,teamName);
    }
}
