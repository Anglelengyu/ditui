package net.hongkuang.ditui.project.busi.orderCancelApproval.service;

import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;

import java.util.List;

/**
 * 订单取消审批 服务层
 *
 * @author yj
 * @date 2019-01-11
 */
public interface IOrderCancelApprovalService {
    /**
     * 查询订单取消审批信息
     *
     * @param id 订单取消审批ID
     * @return 订单取消审批信息
     */
    OrderCancelApproval selectOrderCancelApprovalById(Integer id);

    /**
     * 查询订单取消审批列表
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 订单取消审批集合
     */
    List<OrderCancelApproval> selectOrderCancelApprovalList(OrderCancelApproval orderCancelApproval);

    /**
     * 新增订单取消审批
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 结果
     */
    int insertOrderCancelApproval(OrderCancelApproval orderCancelApproval);

    /**
     * 修改订单取消审批
     *
     * @param orderCancelApproval 订单取消审批信息
     * @return 结果
     */
    int updateOrderCancelApproval(OrderCancelApproval orderCancelApproval);

    /**
     * 删除订单取消审批信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderCancelApprovalByIds(String ids);

    /**
     * 通过取消申请
     *
     * @param ids
     * @return
     */
    int pass(String ids);

    /**
     * 驳回取消申请
     *
     * @param ids
     * @return
     */
    int reject(String ids);
}
