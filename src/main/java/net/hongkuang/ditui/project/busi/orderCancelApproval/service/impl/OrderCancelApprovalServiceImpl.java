package net.hongkuang.ditui.project.busi.orderCancelApproval.service.impl;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.orderCancelApproval.mapper.OrderCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.orderCancelApproval.service.IOrderCancelApprovalService;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskCancelApproval.mapper.TaskCancelApprovalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单取消审批 服务层实现
 *
 * @author yj
 * @date 2019-01-11
 */
@Service
public class OrderCancelApprovalServiceImpl implements IOrderCancelApprovalService {
    @Autowired
    private OrderCancelApprovalMapper orderCancelApprovalMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskCancelApprovalMapper taskCancelApprovalMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private SaleOrderMapper saleOrderMapper;

    /**
     * 查询订单取消审批信息
     *
     * @param id 订单取消审批ID
     * @return 订单取消审批信息
     */
    @Override
    public OrderCancelApproval selectOrderCancelApprovalById(Integer id) {
        return orderCancelApprovalMapper.selectOrderCancelApprovalById(id);
    }

    /**
     * 查询订单取消审批列表
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 订单取消审批集合
     */
    @Override
    public List<OrderCancelApproval> selectOrderCancelApprovalList(OrderCancelApproval orderCancelApproval) {
        return orderCancelApprovalMapper.selectOrderCancelApprovalList(orderCancelApproval);
    }

    /**
     * 新增订单取消审批
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 结果
     */
    @Override
    public int insertOrderCancelApproval(OrderCancelApproval orderCancelApproval) {
        return orderCancelApprovalMapper.insertOrderCancelApproval(orderCancelApproval);
    }

    /**
     * 修改订单取消审批
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 结果
     */
    @Override
    public int updateOrderCancelApproval(OrderCancelApproval orderCancelApproval) {
        return orderCancelApprovalMapper.updateOrderCancelApproval(orderCancelApproval);
    }

    /**
     * 删除订单取消审批对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderCancelApprovalByIds(String ids) {
        return orderCancelApprovalMapper.deleteOrderCancelApprovalByIds(Convert.toStrArray(ids));
    }


    /**
     * 通过取消申请
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int pass(String id) {
        String[] ids = id.split(",");
        List<String> orderIdList = new ArrayList<>();
        List<OrderCancelApproval> orderCancelApprovalList = orderCancelApprovalMapper.selectOrderCancelApprovalByIds(ids);
        for (OrderCancelApproval orderCancelApproval : orderCancelApprovalList) {
            orderIdList.add(orderCancelApproval.getOrderId());
        }
        List<Order> orderList = orderMapper.selectOrderListByOrderIdList(orderIdList);
        int orderResult = 0;
        List<String> saleOrderIdList = new ArrayList<>();
        List<String> cancelOrderIdList = new ArrayList<>();
        List<String> removeTaskIdList = new ArrayList<>();
        List<Long> orderLongIdList = new ArrayList<>();
        for (Order order : orderList) {
            orderLongIdList.add(order.getId());
            Task task = taskMapper.selectTaskByOrderId(order.getOrderId());
            if (task == null) {
                orderResult++;
                continue;
            }
            if (order.getStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                saleOrderIdList.add(order.getOrderId());
            }
            if (order.getStatus().compareTo(TaskTaskStatus.CANCEL.getCode()) == 0) {
                cancelOrderIdList.add(order.getOrderId());
            }
            if (task.getOrderNum() - 1 <= 0) {
                // 删除任务
                removeTaskIdList.add(task.getTaskId());
                orderResult++;
            } else {
                Task updateTask = new Task();
                updateTask.setId(task.getId());
                updateTask.setTaskId(task.getTaskId());
                updateTask.setTaskCommission(task.getTaskCommission() - order.getCommission());
                updateTask.setTaskCorpus(task.getTaskCorpus() - order.getUnitPrice());
                updateTask.setOrderNum(task.getOrderNum() - 1);
                orderResult += taskMapper.updateTask(updateTask);
            }
        }
        if (!removeTaskIdList.isEmpty()) {
            String[] removeTaskIdArray = removeTaskIdList.toArray(new String[removeTaskIdList.size()]);
            // 删除任务取消
            taskCancelApprovalMapper.deleteTaskCancelApprovalByTaskIdList(removeTaskIdList);
            // 删除业务员任务
            saleTaskMapper.deleteByTaskIds(removeTaskIdArray);
            // 删除任务
            int removeTaskResult = taskMapper.deleteTaskByTaskIds(removeTaskIdArray);
            if (removeTaskResult != removeTaskIdList.size()) {
                throw new BusinessException("订单审核通过失败，删除关联任务失败");
            }
            // 删除关联任务时，需将订单待生成订单
            if (!orderLongIdList.isEmpty()) {
                // 更新相应订单状态为待生成任务订单
                orderMapper.updateOrderAllocStatusInIds(orderLongIdList, OrderAllocatStatus.UNFINISHED.getCode()
                        , OrderAllocatStatus.COMPLETE.getCode());
                orderMapper.updateOrderAllocStatusInIds(orderLongIdList, OrderAllocatStatus.UNFINISHED.getCode()
                        , OrderAllocatStatus.HISTORY.getCode());
            }
        }
        if (!cancelOrderIdList.isEmpty()) {
            // 删除订单取消申请
            int cancelOrderResult = orderCancelApprovalMapper.deleteOrderCancelApprovalByOrderIdList(cancelOrderIdList);
            if (cancelOrderResult != cancelOrderIdList.size()) {
                throw new BusinessException("订单审核通过失败，删除关联取消订单申请失败");
            }
        }
        if (!saleOrderIdList.isEmpty()) {
            // 删除业务员接单
            int saleOrderResult = saleOrderMapper.deleteSaleOrderByOrderIdList(saleOrderIdList);
            if (saleOrderResult != saleOrderIdList.size()) {
                // throw new BusinessException("订单审核通过失败，删除关联销售订单申请失败");
                LogUtils.getAccessLog().info("订单审核通过失败，删除关联销售订单申请失败, {}", saleOrderIdList);
            }
        }
        // 更新状态为审核通过
        int count = orderCancelApprovalMapper.pass(ids);
        if (count != orderResult) {
            LogUtils.getAccessLog().info("订单审核通过失败, 更新订单信息失败，{},{},{} ", ids, count, orderResult);
        }
        // 更新相应的订单状态为未接单
        orderCancelApprovalMapper.updateOrderStatusByOrderIds(ids);
        return count;
    }

    /**
     * 驳回取消申请
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int reject(String id) {
        String[] orderIds = id.split(",");
        int count = orderCancelApprovalMapper.reject(orderIds);
        // 修改状态
        int result = orderMapper.updateOrderStatusInCancelIds(Arrays.asList(orderIds), OrderStatus.COMPLETE.getCode(), OrderStatus.PLACEMENT.getCode());
        if (result <= 0) {
            throw new BusinessException("订单驳回失败，请重试");
        }
        return count;
    }

}
