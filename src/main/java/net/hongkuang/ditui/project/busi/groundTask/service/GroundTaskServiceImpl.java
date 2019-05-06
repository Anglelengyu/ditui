package net.hongkuang.ditui.project.busi.groundTask.service;

import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.mapper.EmployeeMapper;
import net.hongkuang.ditui.project.busi.groundEmployeeOrder.domain.GroundEmployeeOrder;
import net.hongkuang.ditui.project.busi.groundEmployeeOrder.mapper.GroundEmployeeOrderMapper;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.domain.GroundEmployeeTask;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.mapper.GroundEmployeeTaskMapper;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTaskVo;
import net.hongkuang.ditui.project.busi.groundTask.domain.SearchGroundTask;
import net.hongkuang.ditui.project.busi.groundTask.enums.GroundTaskAllocType;
import net.hongkuang.ditui.project.busi.groundTask.mapper.GroundTaskMapper;
import net.hongkuang.ditui.project.busi.groundTaskOrder.mapper.GroundTaskOrderMapper;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.mapper.RealTimeGroundTaskMapper;
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
public class GroundTaskServiceImpl implements IGroundTaskService {

    @Autowired
    private GroundTaskMapper groundTaskMapper;
    @Autowired
    private GroundTaskOrderMapper groundTaskOrderMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private RealTimeGroundTaskMapper realTimeGroundTaskMapper;
    @Autowired
    private GroundEmployeeOrderMapper groundEmployeeOrderMapper;
    @Autowired
    private GroundEmployeeTaskMapper groundEmployeeTaskMapper;
    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    @Override
    public List<GroundTaskVo> selectTaskList(SearchGroundTask task) {
        if (!StringUtils.isEmpty(task.getStartTime())) {
            task.setStartTime(task.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(task.getEndTime())) {
            task.setEndTime(task.getEndTime() + " 23:59:59");
        }
        return groundTaskMapper.selectGroundTaskList(task);
    }

    @Override
    public GroundTask selectGroundTaskById(Long id) {
        return groundTaskMapper.selectGroundTaskById(id);
    }

    @Override
    public GroundTask selectGroundTaskByOrderId(String orderId) {
        return groundTaskMapper.selectGroundTaskByOrderId(orderId);
    }

    @Override
    public int updateGroundTaskTeamRemark(GroundTask groundTask) {
        return groundTaskMapper.updateGroundTaskTeamRemark(groundTask);
    }

    /**
     * 删除任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteGroundTaskByIds(String ids) {
        List<GroundTask> taskList = groundTaskMapper.selectGroundTaskByIds(Convert.toStrArray(ids));
        if (taskList == null || taskList.isEmpty()) {
            throw new BusinessException("请选择删除任务");
        }
        List<String> taskIdList = new ArrayList<>(taskList.size());
        List<String> cancelTaskIdList = new ArrayList<>();
        List<String> receivedTaskIdList = new ArrayList<>();
        for (GroundTask groundTask : taskList) {
            taskIdList.add(groundTask.getTaskId());
            if (TaskTaskStatus.CANCEL.getCode().equals(groundTask.getTaskStatus())) {
                cancelTaskIdList.add(groundTask.getTaskId());
            }
            List<String> orderIdList = groundTaskOrderMapper.selectGroundTaskOrderIdByTaskId(groundTask.getTaskId());
            if (!orderIdList.isEmpty()) {
                // 删除订单
                tbTransactionOrderMapper.deleteTbTransactionOrderByOrderIds(orderIdList.toArray(new String[orderIdList.size()]));
                // 删除任务订单
                groundTaskOrderMapper.deleteGroundTaskOrderByOrderIdList(orderIdList);
                // TODO 删除取消订单
//                orderCancelApprovalMapper.deleteOrderCancelApprovalByOrderIdList(orderIdList);
            }
            if (groundTask.getTaskStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                receivedTaskIdList.add(groundTask.getTaskId());
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
        return groundTaskMapper.deleteGroundTaskByIds(Convert.toStrArray(ids));
    }

    @Override
    public int updateBatchGroundTaskStatusAllot(List<String> taskIds) {
        return groundTaskMapper.updateBatchGroundTaskStatusAllot(taskIds);
    }

    @Override
    @Transactional
    public int toReal(String taskDate, String startTime, String stopTime, String taskIdStr,Long teamId) {
        List<String> taskIds = Arrays.asList(taskIdStr.split(","));
        List<RealTimeGroundTask> realTimeGroundTaskList = new ArrayList<>(taskIds.size());
        RealTimeGroundSetting realTimeGroundSetting = new RealTimeGroundSetting();
        realTimeGroundSetting.setStartTimeNode(startTime);
        realTimeGroundSetting.setStopTimeNode(stopTime);
        realTimeGroundSetting.setTaskDate(taskDate);
//        List<String> taskIdList = new ArrayList<>();
        AtomicReference<GroundTask> task = new AtomicReference<>();
        AtomicReference<RealTimeGroundTask> realTimeTask = new AtomicReference<>();
        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        taskIds.forEach(taskId -> {
            task.set(new GroundTask());
            task.get().setTaskId(taskId);
            realTimeTask.set(new RealTimeGroundTask(realTimeGroundSetting, task.get()));
            realTimeTask.get().setCreateBy(user);
            realTimeTask.get().setUpdateBy(user);
            realTimeTask.get().setCreateTime(nowDate);
            realTimeTask.get().setUpdateTime(nowDate);
            realTimeTask.get().setTeamId(teamId);
            realTimeTask.get().setOrLock(0);
            realTimeGroundTaskList.add(realTimeTask.get());
        });
        // 更新任务状态为可分配
        int updateTaskResult = groundTaskMapper.updateBatchStatusByIds(taskIds,
                TbTransactionOrderAllocatStatus.COMPLETE.getCode(), TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                , GroundTaskAllocType.REAL_TIME.getCode()
                , realTimeGroundSetting.getStartTimeNode(), realTimeGroundSetting.getStopTimeNode());
        // 插入实时任务
        int insertTaskResult = realTimeGroundTaskMapper.batchInsert(realTimeGroundTaskList);
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
                List<GroundEmployeeOrder> groundEmployeeOrderList = new ArrayList<>();
                List<String> updateOrderIdList = new ArrayList<>();
                List<GroundEmployeeTask> groundEmployeeTaskList = new ArrayList<>();
                // 获取关联订单
                List<String> orderIdList = groundTaskOrderMapper.selectGroundTaskOrderIdByTaskIds(taskIds);
                for (String orderId : orderIdList) {
                    GroundEmployeeOrder groundEmployeeOrder = GroundEmployeeOrder.build(orderId, employeeId);
                    groundEmployeeOrder.setCreateTime(date);
                    groundEmployeeOrder.setUpdateTime(date);
                    groundEmployeeOrder.setTeamId(teamId);
                    groundEmployeeOrderList.add(groundEmployeeOrder);
                }
                updateOrderIdList.addAll(orderIdList);
                // 修改任务状态

                groundTaskMapper.updateBatchTaskStatusByIds(taskIds, TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), employeeId);
                groundTaskMapper.updateAllTaskStatus(TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), taskIds);
                for(String taskId :taskIds){
                    GroundEmployeeTask groundEmployeeTask = GroundEmployeeTask.build(taskId, employeeId);
                    groundEmployeeTask.setCreateTime(date);
                    groundEmployeeTask.setUpdateTime(date);
                    groundEmployeeTask.setTeamId(teamId);
                    groundEmployeeTaskList.add(groundEmployeeTask);
                }
                groundEmployeeTaskMapper.batchInsert(groundEmployeeTaskList);
                // 创建业务员订单表
                groundEmployeeOrderMapper.batchInsert(groundEmployeeOrderList);
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
        GroundTask canceling = groundTaskMapper.checkGroundTaskStatusByEmployeeId(employee.getEmployeeId(), TaskTaskStatus.CANCEL.getCode());
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
        GroundTask running = groundTaskMapper.checkGroundTaskStatusByEmployeeId(employee.getEmployeeId(), TaskTaskStatus.RECEIVED.getCode());

        if (running != null) {
            result.put("status", 1);
            result.put("taskId", running.getTaskId());
            return result;
        }

        result.put("status", 0);
        return result;
    }

    @Override
    public boolean selectGroundTaskIsDistribution(String[] ids) {
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
