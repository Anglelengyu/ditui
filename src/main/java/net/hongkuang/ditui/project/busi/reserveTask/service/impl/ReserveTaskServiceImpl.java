package net.hongkuang.ditui.project.busi.reserveTask.service.impl;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.exception.BusinessDataException;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.exception.ExceptionCode;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.api.dto.TodoTaskReqVo;
import net.hongkuang.ditui.project.api.dto.TodoTaskRespVo;
import net.hongkuang.ditui.project.api.service.ITodoTaskService;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.reserveTask.domain.ReserveTask;
import net.hongkuang.ditui.project.busi.reserveTask.mapper.ReserveTaskMapper;
import net.hongkuang.ditui.project.busi.reserveTask.service.IReserveTaskService;
import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 储备任务 服务层实现
 *
 * @author yj
 * @date 2019-01-03
 */
@Service(value = "reserveTaskService")
public class ReserveTaskServiceImpl implements IReserveTaskService, ITodoTaskService {
    private ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    private ReserveTaskMapper reserveTaskMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SaleOrderMapper saleOrderMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询储备任务信息
     *
     * @param id 储备任务ID
     * @return 储备任务信息
     */
    @Override
    public ReserveTask selectReserveTaskById(Long id) {
        return reserveTaskMapper.selectReserveTaskById(id);
    }

    /**
     * 查询储备任务列表
     *
     * @param reserveTask 储备任务信息
     * @return 储备任务集合
     */
    @Override
    public List<ReserveTask> selectReserveTaskList(ReserveTask reserveTask) {
        return reserveTaskMapper.selectReserveTaskList(reserveTask);
    }

    /**
     * 新增储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    @Override
    public int insertReserveTask(ReserveTask reserveTask) {
        return reserveTaskMapper.insertReserveTask(reserveTask);
    }

    /**
     * 修改储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    @Override
    public int updateReserveTask(ReserveTask reserveTask) {
        return reserveTaskMapper.updateReserveTask(reserveTask);
    }

    /**
     * 删除储备任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReserveTaskByIds(String ids) {
        return reserveTaskMapper.deleteReserveTaskByIds(Convert.toStrArray(ids));
    }

    @Override
    public int insertBatchReserveTask(List<String> taskIds) {
        List<ReserveTask> reserveTasks = new ArrayList<>();
        AtomicReference<ReserveTask> reserveTask = new AtomicReference();
        String reserveDate = DateUtils.getDate();
        taskIds.forEach(taskId -> {
            reserveTask.set(new ReserveTask());
            reserveTask.get().setCreateBy(ShiroUtils.getLoginName());
            reserveTask.get().setReserveId(RandomUtil.genString());
            reserveTask.get().setReserveDate(reserveDate);
            reserveTask.get().setStatus(0);
            reserveTask.get().setTaskId(taskId);
            reserveTasks.add(reserveTask.get());
        });
        return reserveTasks != null ? reserveTaskMapper.insertBatchReserveTask(reserveTasks) : 0;
    }

    @Override
    public Integer selectCountByReserveDate(String taskDate) {
        return reserveTaskMapper.selectCountByReserveDate(taskDate);
    }

    @Override
    public List<TodoTaskRespVo> allocTodoTaskList(TodoTaskReqVo taskReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        String saleId = salesman.getSaleId();
        boolean lockResult = false;
        LogUtils.getAccessLog().info("开始分配任务,储备任务, 争抢锁开始 userID:{}", taskReqVo.getUserId());
        try {
            lockResult = reentrantLock.tryLock(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LogUtils.getAccessLog().info("开始分配任务,储备任务, 抢锁失败 userID:{}", taskReqVo.getUserId());
            throw new BusinessException("当前有其他业务员在抢储备任务，请稍后重试");
        }
        if (!lockResult) {
            LogUtils.getAccessLog().info("开始分配任务,储备任务, 抢锁失败 userID:{}", taskReqVo.getUserId());
            throw new BusinessException("当前有其他业务员在抢储备任务，请稍后重试");
        }

        LogUtils.getAccessLog().info("开始分配任务,储备任务, 抢锁成功 userID:{}", taskReqVo.getUserId());
        // 判断当前用户是否有为完成的任务
        String taskDate = DateUtils.getDate();
        List<TodoTaskRespVo> todoTaskRespVoList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try {
            Task task = taskMapper.getFirstTask(saleId, TaskTaskStatus.RECEIVED.getCode());
            if (task != null) {
                throw new BusinessDataException(ExceptionCode.CODE_TASK_WAIT, "当前有未完成任务，不可领取信息任务", task.getTaskId());
            }
            PageHelper.startPage(1, taskReqVo.getTaskCount());
            List<String> todoTaskList = reserveTaskMapper.selectTodoReserveTaskList(taskDate);
            for (String taskId : todoTaskList) {
                todoTaskRespVoList.add(new TodoTaskRespVo(taskId));
            }
            if (todoTaskList.isEmpty()) {
                throw new BusinessException("目前已无实时任务分配，请选择备份任务");
            }
            // 修改任务状态
            taskMapper.updateBatchTaskStatusByIds(todoTaskList, TaskTaskStatus.RECEIVED.getCode()
                    , TaskTaskStatus.WAIT.getCode(), saleId);

            List<SaleOrder> saleOrderList = new ArrayList<>();
            List<SaleTask> saleTaskList = new ArrayList<>();
            List<String> updateOrderIdList = new ArrayList<>();
            Date date = DateUtils.getNowDate();
            for (String taskId : todoTaskList) {
                // 关联任务
                SaleTask saleTask = SaleTask.build(taskId, saleId);
                saleTask.setCreateTime(date);
                saleTask.setUpdateTime(date);
                saleTaskList.add(saleTask);

                // 获取关联订单
                List<String> orderIdList = taskOrderMapper.selectOrderIdByTaskId(taskId);
                for (String orderId : orderIdList) {
                    SaleOrder saleOrder = SaleOrder.build(orderId, saleId);
                    saleOrder.setCreateTime(date);
                    saleOrder.setUpdateTime(date);
                    saleOrderList.add(saleOrder);
                }
                updateOrderIdList.addAll(orderIdList);
            }
            // 创建业务员任务表
            saleTaskMapper.batchInsert(saleTaskList);
            // 创建业务员订单表
            saleOrderMapper.batchInsert(saleOrderList);
            // 修改订单状态
            orderMapper.updateOrderStatusInOrderIds(updateOrderIdList, OrderStatus.COMPLETE.getCode(), OrderStatus.UNFINISHED.getCode());
        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.getAccessLog().info("开始分配任务,储备任务, 锁释放 userID:{}, cost time:{}", taskReqVo.getUserId()
                    , endTime - startTime);
            reentrantLock.unlock();
        }
        return todoTaskRespVoList;
    }
}
