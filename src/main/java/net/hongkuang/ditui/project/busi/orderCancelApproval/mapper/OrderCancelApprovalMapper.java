package net.hongkuang.ditui.project.busi.orderCancelApproval.mapper;

import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单取消审批 数据层
 *
 * @author yj
 * @date 2019-01-11
 */
@Repository
public interface OrderCancelApprovalMapper {
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
     * 删除订单取消审批
     *
     * @param id 订单取消审批ID
     * @return 结果
     */
    int deleteOrderCancelApprovalById(Integer id);

    /**
     * 批量删除订单取消审批
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderCancelApprovalByIds(String[] ids);

    int reject(String[] ids);

    int pass(String[] ids);

    void updateOrderStatusByOrderIds(String[] ids);

    void updateTaskCommissionAndCorpus(String[] ids);

    void deleteSaleOrderByIds(String[] ids);

    void deleteTaskOrderByIds(String[] ids);

    int deleteOrderCancelApprovalByOrderIdList(List<String> orderIdList);

    List<OrderCancelApproval> selectOrderCancelApprovalByIds(String[] ids);
}