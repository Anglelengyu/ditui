package net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.service;

import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.domain.TbTransactionOrderCancelApproval;

import java.util.List;

/**
 * 订单取消审批 服务层
 *
 * @author:zy
 * @date: 2019/4/22
 */
public interface ITbTransactionOrderCancelApprovalService {
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
     * 删除订单取消审批信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTbTransactionOrderCancelApprovalByIds(String ids);

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
