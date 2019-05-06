package net.hongkuang.ditui.project.busi.taskOrder.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskOrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.enums.TaskStatus;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskOrder.domain.TaskOrder;
import net.hongkuang.ditui.project.busi.taskOrder.dto.TaskOrderDto;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务订单 服务层实现
 *
 * @author yj
 * @date 2019-01-03
 */
@Service
public class TaskOrderServiceImpl implements ITaskOrderService {
    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    @Override
    public TaskOrder selectTaskOrderById(Long id) {
        return taskOrderMapper.selectTaskOrderById(id);
    }

    /**
     * 查询任务订单列表
     *
     * @param taskOrder 任务订单信息
     * @return 任务订单集合
     */
    @Override
    public List<TaskOrder> selectTaskOrderList(TaskOrder taskOrder) {
        return taskOrderMapper.selectTaskOrderList(taskOrder);
    }

    /**
     * 新增任务订单
     *
     * @param taskOrderDto 任务订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTaskOrder(TaskOrderDto taskOrderDto) {
        Order order = orderMapper.selectOrderById(taskOrderDto.getOrderId());
        if (order == null || order.getStatus() != OrderStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前订单不存在");
        }
        Task task = taskMapper.selectTaskByTaskId(taskOrderDto.getTaskId());
        if (task == null || task.getTaskStatus() != TaskStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前任务不处于未接单状态");
        }
        List<TaskOrder> orders = taskOrderMapper.selectTaskOrderByOrderId(order.getOrderId());
        if (orders != null && orders.size() > 0) {
            throw new BusinessException("该订单已分配，不可重复分配");
        }
        String operator = ShiroUtils.getLoginName();
        TaskOrder taskOrder = new TaskOrder();
        taskOrder.setOrderId(order.getOrderId());
        taskOrder.setCreateTime(new Date());
        taskOrder.setTaskId(task.getTaskId());
        taskOrder.setCreateBy(operator);
        taskOrder.setTaskOrderId(order.getOrderId());
        taskOrder.setStatus(TaskOrderStatus.NORMAL.getCode());
        taskOrder.setUpdateTime(new Date());
        taskOrder.setUpdateBy(operator);
        taskOrderMapper.insertTaskOrder(taskOrder);

        order.setAllocatStatus(OrderAllocatStatus.COMPLETE.getCode());
        orderMapper.updateOrder(order);

        task.setTaskCommission(task.getTaskCommission() + order.getCommission());
        task.setTaskCorpus(task.getTaskCorpus() + order.getUnitPrice());
        task.setOrderNum(task.getOrderNum() + 1);

        return taskMapper.updateTask(task);
    }

    /**
     * 修改任务订单
     *
     * @param taskOrder 任务订单信息
     * @return 结果
     */
    @Override
    public int updateTaskOrder(TaskOrder taskOrder) {
        return taskOrderMapper.updateTaskOrder(taskOrder);
    }

    /**
     * 删除任务订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
//	@Transactional
    public int deleteTaskOrderByIds(String taskId, String ids) {
        Task task = taskMapper.selectTaskByTaskId(taskId);
        if (task == null || task.getTaskStatus() != TaskStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前任务不处于未接单状态");
        }
        int taskResult = 1;
        orderMapper.selectOrderListByIds(Convert.toStrArray(ids)).forEach(order -> {
            if (order == null || order.getStatus() != OrderStatus.UNFINISHED.getCode()) {
                throw new BusinessException("当前订单不存在");
            }

            String operator = ShiroUtils.getLoginName();
            order.setAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
            order.setUpdateTime(new Date());
            order.setUpdateBy(operator);
            orderMapper.updateOrder(order);

            taskOrderMapper.deleteTaskOrderByOrderId(task.getTaskId(), order.getOrderId());
            task.setTaskCommission(task.getTaskCommission() - order.getCommission());
            task.setTaskCorpus(task.getTaskCorpus() - order.getUnitPrice());
            task.setOrderNum(task.getOrderNum() - 1);

            taskMapper.updateTask(task);
        });
        return taskResult;
    }

}
