package net.hongkuang.ditui.project.busi.task.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.api.dto.*;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.mapper.ImgMapper;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.orderCancelApproval.mapper.OrderCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;
import net.hongkuang.ditui.project.busi.realTimeTask.mapper.RealTimeTaskMapper;
import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.task.domain.SearchTask;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.domain.TaskVo;
import net.hongkuang.ditui.project.busi.task.dto.CheckOrderResult;
import net.hongkuang.ditui.project.busi.task.enums.TaskAllocType;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskCancelApproval.domain.TaskCancelApproval;
import net.hongkuang.ditui.project.busi.taskCancelApproval.enums.TaskCancelApprovalStatus;
import net.hongkuang.ditui.project.busi.taskCancelApproval.mapper.TaskCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 任务 服务层实现
 *
 * @author yj
 * @date 2018-12-30
 */
@Service
public class TaskServiceImpl implements ITaskService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    TaskCancelApprovalMapper taskCancelApprovalMapper;
    @Autowired
    OrderCancelApprovalMapper orderCancelApprovalMapper;

    @Autowired
    private RealTimeTaskMapper realTimeTaskMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private SaleOrderMapper saleOrderMapper;

    /**
     * 查询任务信息
     *
     * @param id 任务ID
     * @return 任务信息
     */
    @Override
    public Task selectTaskById(Long id) {
        return taskMapper.selectTaskById(id);
    }

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    @Override
    public List<TaskVo> selectTaskList(SearchTask task) {
        if (!StringUtils.isEmpty(task.getStartTime())) {
            task.setStartTime(task.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(task.getEndTime())) {
            task.setEndTime(task.getEndTime() + " 23:59:59");
        }
        return taskMapper.selectTaskList(task);
    }

    /**
     * 新增任务
     *
     * @param task 任务信息
     * @return 结果
     */
    @Override
    public int insertTask(Task task) {
        return taskMapper.insertTask(task);
    }

    /**
     * 修改任务
     *
     * @param task 任务信息
     * @return 结果
     */
    @Override
    public int updateTask(Task task) {
        return taskMapper.updateTask(task);
    }

    /**
     * 删除任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteTaskByIds(String ids) {
        List<Task> taskList = taskMapper.selectTaskByIds(Convert.toStrArray(ids));
        if (taskList == null || taskList.isEmpty()) {
            throw new BusinessException("请选择删除任务");
        }
        List<String> taskIdList = new ArrayList<>(taskList.size());
        List<String> cancelTaskIdList = new ArrayList<>();
        List<String> receivedTaskIdList = new ArrayList<>();
        for (Task task : taskList) {
            taskIdList.add(task.getTaskId());
            if (TaskTaskStatus.CANCEL.getCode().equals(task.getTaskStatus())) {
                cancelTaskIdList.add(task.getTaskId());
            }
            List<String> orderIdList = taskOrderMapper.selectOrderIdByTaskId(task.getTaskId());
            if (!orderIdList.isEmpty()) {
                // 删除订单
                orderMapper.deleteOrderByIds(orderIdList.toArray(new String[orderIdList.size()]));
                // 删除任务订单
                taskOrderMapper.deleteTaskOrderByOrderIdList(orderIdList);
                // 删除取消订单
                orderCancelApprovalMapper.deleteOrderCancelApprovalByOrderIdList(orderIdList);
            }
            if (task.getTaskStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                receivedTaskIdList.add(task.getTaskId());
                if (!orderIdList.isEmpty()) {
                    // 删除业务员订单
                    saleOrderMapper.deleteSaleOrderByOrderIdList(orderIdList);
                }
            }
        }
        if (!cancelTaskIdList.isEmpty()) {
            // 删除取消任务
            taskCancelApprovalMapper.deleteTaskCancelApprovalByTaskIdList(taskIdList);
        }
        if (!receivedTaskIdList.isEmpty()) {
            // 删除业务员任务表
            saleTaskMapper.deleteByTaskIds(receivedTaskIdList.toArray(new String[receivedTaskIdList.size()]));
        }
        return taskMapper.deleteTaskByIds(Convert.toStrArray(ids));
    }

    @Override
    public int updateBatchTaskStatusAllot(List<String> taskIds) {
        return taskMapper.updateBatchTaskStatusAllot(taskIds);
    }

    @Override
    public int moveToHistory(Integer type, String taskIds) {
        int resultCount = 0;
        if (type == Constants.ALL_DATA) {
            resultCount = taskMapper.updateAllTaskStatus(OrderAllocatStatus.HISTORY.getCode(), OrderAllocatStatus.COMPLETE.getCode(), null);
        } else {
            resultCount = taskMapper.updateBatchStatusByIds(Arrays.asList(taskIds.split(",")), OrderAllocatStatus.HISTORY.getCode()
                    , OrderAllocatStatus.COMPLETE.getCode(), TaskAllocType.BACKUP.getCode(), "", "");
        }
        return resultCount;
    }

    @Override
    public Task selectTaskByOrderId(String orderId) {
        return taskMapper.selectTaskByOrderId(orderId);
    }

    @Override
    public int updateTaskByTaskIds(String[] taskId) {
        return taskMapper.updateTaskByTaskIds(taskId);
    }

    @Override
    public Task getNotCompleteTask(SimpleTaskReqVo taskReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        return taskMapper.getFirstTask(salesman.getSaleId(), TaskTaskStatus.WAIT.getCode());
    }

    /**
     * @param taskReqVo
     * @return
     */
    @Override
    public TaskDetailRespVo getTaskDetail(SimpleTaskReqVo taskReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskReqVo.getUserId());
        // 获取任务
        Task task = taskMapper.selectTaskByTaskId(taskReqVo.getTaskId());
        if (task == null) {
            throw new BusinessException(taskReqVo.getTaskId() + ", 该任务不存在");
        }
        if (!task.getSaleId().equals(salesman.getSaleId())) {
            throw new BusinessException("该任务不属于你，不可进行操作");
        }
        TaskDetailRespVo taskDetailRespVo = new TaskDetailRespVo();
        taskDetailRespVo.setTaskId(task.getTaskId());
        taskDetailRespVo.setOrderNum(task.getOrderNum());
        taskDetailRespVo.setTaskStatus(task.getTaskStatus());
        // 获取订单信息
        taskDetailRespVo.setOrderList(orderMapper.selectOrderListByOrderId(task.getTaskId()));
        return taskDetailRespVo;
    }

    @Override
    public List<TaskListRespVo> selectTaskList(TaskSearchReqVo taskReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        SearchTask searchTask = new SearchTask();
        BeanUtils.copyBeanProp(searchTask, taskReqVo);
        searchTask.setSaleId(salesman.getSaleId());
        List<Task> taskList = taskMapper.selectTaskList2(searchTask);

        Map<String, List<TaskListRespVo.SimpleTaskVo>> taskMap = new HashMap<>();
        for (Task task : taskList) {
            String date = DateUtils.convertTimeToFormat(task.getCreateTime());
            List<TaskListRespVo.SimpleTaskVo> tasks = taskMap.get(date);
            if (tasks == null) {
                tasks = new ArrayList<>();
                taskMap.put(date, tasks);
            }
            tasks.add(TaskListRespVo.SimpleTaskVo.buildTask(task));
        }
        List<TaskListRespVo> taskListRespVo = new ArrayList<>();
        for (Entry<String, List<TaskListRespVo.SimpleTaskVo>> taskEntry : taskMap.entrySet()) {
            TaskListRespVo listRespVo = new TaskListRespVo();
            listRespVo.setDate(taskEntry.getKey());
            listRespVo.setTaskList(taskEntry.getValue());
            taskListRespVo.add(listRespVo);
        }
        return taskListRespVo;
    }

    @Override
    public int cancel(TaskCancelReqVo taskCancelReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskCancelReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        Task task = taskMapper.selectTaskByTaskId(taskCancelReqVo.getTaskId());
        if (task == null) {
            throw new BusinessException(taskCancelReqVo.getTaskId() + "该任务不存在");
        }
        if (!task.getSaleId().equals(salesman.getSaleId())) {
            throw new BusinessException("该任务订单属于他人，不能进行取消操作");
        }
        // 修改状态
        int result = taskMapper.updateBatchTaskStatusByIds(Arrays.asList(task.getTaskId()), TaskTaskStatus.CANCEL.getCode(), TaskTaskStatus.RECEIVED.getCode(), salesman.getSaleId());
        if (result <= 0) {
            throw new BusinessException("该任务已取消完毕，请勿重复取消");
        }
        // 插入状态
        TaskCancelApproval taskCancelApproval = new TaskCancelApproval();
        taskCancelApproval.setApprovalId(RandomUtil.genString());
        taskCancelApproval.setTaskId(task.getTaskId());
        taskCancelApproval.setRemark("");
        taskCancelApproval.setCreateTime(DateUtils.getNowDate());
        taskCancelApproval.setSaleId(salesman.getSaleId());
        taskCancelApproval.setSalePhone(salesman.getPhone());
        taskCancelApproval.setSaleName(salesman.getName());
        taskCancelApproval.setSaleArea(salesman.getArea());
        taskCancelApproval.setStatus(TaskCancelApprovalStatus.WAIT.getCode());
        taskCancelApprovalMapper.insertTaskCancelApproval(taskCancelApproval);
        return result;
    }

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中 0  正常
     */
    @Override
    public Map<String, Object> checkStatus(String userId) {
        Salesman salesman = salesmanMapper.selectSalesmanById(Long.valueOf(userId));
        Map<String, Object> result = new HashMap<>();
        // 获取当前是否有任务正在进行中
        Task canceling = taskMapper.checkTaskStatusBySaleId(salesman.getSaleId(), TaskTaskStatus.CANCEL.getCode());
        if (canceling != null) {
            result.put("status", 2);
            result.put("taskId", canceling.getTaskId());
            return result;
        }
        Order orderCancaling = orderMapper.checkOrderStatusBySaleId(salesman.getSaleId(), OrderStatus.PLACEMENT.getCode());
        if (orderCancaling != null) {
            result.put("status", 3);
            result.put("taskId", orderCancaling.getTaskId());
            return result;
        }
        Task running = taskMapper.checkTaskStatusBySaleId(salesman.getSaleId(), TaskTaskStatus.RECEIVED.getCode());

        if (running != null) {
            result.put("status", 1);
            result.put("taskId", running.getTaskId());
            return result;
        }

        result.put("status", 0);
        return result;
    }

    @Override
    @Transactional
    public int submitTask(TaskSubmitReqVo taskSubmit) {
        log.info("收到提交任务请求,参数：{}", JSONObject.toJSON(taskSubmit).toString());
        List<Order> orders = JSONArray.parseArray(taskSubmit.getOrderInfo()).toJavaList(Order.class);
        // 更新任务状态
        taskSubmit.setTaskStatus(TaskTaskStatus.COMPLETED.getCode());
        taskMapper.updateTaskStatusByTaskId(taskSubmit);
        if (StringUtils.isEmpty(taskSubmit.getCheckOrderResult())) {
            throw new BusinessException("订单信息异常");
        }
        List<CheckOrderResult> checkOrderResultList = JSONArray.parseArray(taskSubmit.getCheckOrderResult()).toJavaList(CheckOrderResult.class);
        Map<String, CheckOrderResult> checkOrderResultMap = checkOrderResultList.stream()
                .collect(Collectors.toMap(CheckOrderResult::getOrderId, a -> a, (k1, k2) -> k1));
        // 更新订单状态
        orders.forEach(order -> {
            order.setStatus(OrderStatus.FINISH.getCode());
            CheckOrderResult checkOrderResult = checkOrderResultMap.get(order.getOrderId());
            if (checkOrderResult != null) {
                order.setOrderNo(checkOrderResult.getTid());
                if (!StringUtils.isEmpty(checkOrderResult.getBuyerNick()) && checkOrderResult.getPayment() != null) {
                    order.setBuyerNick(checkOrderResult.getBuyerNick());
                    order.setPayment(UnitUtils.unitFen(checkOrderResult.getPayment()));
                }
                order.setMarkStatus(checkOrderResult.getStatus());
                order.setMarkComment(checkOrderResult.getMsg());
            }
        });
        orderMapper.updateOrderStatusAndOrderNo(orders);
        // 插入图片信息
        List<Img> imgs = JSONArray.parseArray(taskSubmit.getTaskInfo()).toJavaList(Img.class);
        imgMapper.insertBatchImg(imgs);
        return 1;
    }

    @Override
    public int toReal(String taskDate, String startTime, String stopTime, String taskIdStr) {
        List<String> taskIds = Arrays.asList(taskIdStr.split(","));
        List<RealTimeTask> realTimeTaskList = new ArrayList<>(taskIds.size());
        RealTimeSetting realTimeSetting = new RealTimeSetting();
        realTimeSetting.setStartTimeNode(startTime);
        realTimeSetting.setStopTimeNode(stopTime);
        realTimeSetting.setTaskDate(taskDate);
//        List<String> taskIdList = new ArrayList<>();
        AtomicReference<Task> task = new AtomicReference<>();
        AtomicReference<RealTimeTask> realTimeTask = new AtomicReference<>();
        String user = ShiroUtils.getLoginName();
        Date nowDate = DateUtils.getNowDate();
        taskIds.forEach(taskId -> {
            task.set(new Task());
            task.get().setTaskId(taskId);
            realTimeTask.set(new RealTimeTask(realTimeSetting, task.get()));
            realTimeTask.get().setCreateBy(user);
            realTimeTask.get().setUpdateBy(user);
            realTimeTask.get().setCreateTime(nowDate);
            realTimeTask.get().setUpdateTime(nowDate);
            realTimeTaskList.add(realTimeTask.get());
        });
        // 更新任务状态为可分配
        int updateTaskResult = taskMapper.updateBatchStatusByIds(taskIds,
                OrderAllocatStatus.COMPLETE.getCode(), OrderAllocatStatus.UNFINISHED.getCode()
                , TaskAllocType.REAL_TIME.getCode()
                , realTimeSetting.getStartTimeNode(), realTimeSetting.getStopTimeNode());
        // 插入实时任务
        int insertTaskResult = realTimeTaskMapper.batchInsert(realTimeTaskList);
        if (updateTaskResult != insertTaskResult) {
            throw new BusinessException("添加实时任务失败，请重试");
        }
        return 1;
    }

    @Override
    @Transactional
    public int toSale(String taskId, String saleId, String userId) {
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
                List<SaleOrder> saleOrderList = new ArrayList<>();
                List<String> updateOrderIdList = new ArrayList<>();
                List<SaleTask> saleTaskList = new ArrayList<>();
                // 获取关联订单
                List<String> orderIdList = taskOrderMapper.selectOrderIdByTaskId(taskId);
                for (String orderId : orderIdList) {
                    SaleOrder saleOrder = SaleOrder.build(orderId, saleId);
                    saleOrder.setCreateTime(date);
                    saleOrder.setUpdateTime(date);
                    saleOrderList.add(saleOrder);
                }
                updateOrderIdList.addAll(orderIdList);
                // 修改任务状态
                List<String> taskIds = new ArrayList() {{
                    add(taskId);
                }};
                taskMapper.updateBatchTaskStatusByIds(taskIds, TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), saleId);
                taskMapper.updateAllTaskStatus(TaskTaskStatus.RECEIVED.getCode()
                        , TaskTaskStatus.WAIT.getCode(), taskIds);
                SaleTask saleTask = SaleTask.build(taskId, saleId);
                saleTask.setCreateTime(date);
                saleTask.setUpdateTime(date);
                saleTaskList.add(saleTask);
                saleTaskMapper.batchInsert(saleTaskList);
                // 创建业务员订单表
                saleOrderMapper.batchInsert(saleOrderList);
                // 修改订单状态
                orderMapper.updateOrderStatusInOrderIds(updateOrderIdList, OrderStatus.COMPLETE.getCode(), OrderStatus.UNFINISHED.getCode());
                return 1;
        }
        return 0;
    }


}
