package net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.mapper;

import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.domain.TbTransactionOrderCancelApproval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单取消审批 数据层
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Repository
public interface TbTransactionOrderCancelApprovalMapper {
    /**
     * 查询订单取消审批信息
     *
     * @param id 订单取消审批ID
     * @return 订单取消审批信息
     */
    TbTransactionOrderCancelApproval selectTbTransactionOrderCancelApprovalById(Integer id);

    /**
     * 查询订单取消审批列表
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 订单取消审批集合
     */
    List<TbTransactionOrderCancelApproval> selectTbTransactionOrderCancelApprovalList(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval);

    /**
     * 新增订单取消审批
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 结果
     */
    int insertTbTransactionOrderCancelApproval(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval);

    /**
     * 修改订单取消审批
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 结果
     */
    int updateTbTransactionOrderCancelApproval(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval);

    /**
     * 删除订单取消审批
     *
     * @param id 订单取消审批ID
     * @return 结果
     */
    int deleteTbTransactionOrderCancelApprovalById(Integer id);

    /**
     * 批量删除订单取消审批
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTbTransactionOrderCancelApprovalByIds(String[] ids);

    int reject(String[] ids);

    int pass(String[] ids);

    void updateTbTransactionOrderStatusByOrderIds(String[] ids);

    void updateTaskCommissionAndCorpus(String[] ids);

    void deleteSaleOrderByIds(String[] ids);

    void deleteTaskOrderByIds(String[] ids);

    int deleteTbTransactionOrderCancelApprovalByOrderIdList(List<String> orderIdList);

    List<TbTransactionOrderCancelApproval> selectTbTransactionOrderCancelApprovalByIds(String[] ids);
}