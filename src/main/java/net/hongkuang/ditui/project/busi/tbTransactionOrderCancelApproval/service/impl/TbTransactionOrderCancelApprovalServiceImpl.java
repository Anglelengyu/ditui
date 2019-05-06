package net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.service.impl;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.project.busi.groundEmployeeOrder.mapper.GroundEmployeeOrderMapper;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.mapper.GroundEmployeeTaskMapper;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.mapper.GroundTaskMapper;
import net.hongkuang.ditui.project.busi.groundTaskCancelApproval.mapper.GroundTaskCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.domain.TbTransactionOrderCancelApproval;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.mapper.TbTransactionOrderCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.service.ITbTransactionOrderCancelApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单取消审批 服务层实现
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Service
public class TbTransactionOrderCancelApprovalServiceImpl implements ITbTransactionOrderCancelApprovalService {
    @Autowired
    private TbTransactionOrderCancelApprovalMapper tbTransactionOrderCancelApprovalMapper;

    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private GroundTaskMapper groundTaskMapper;
    @Autowired
    private GroundTaskCancelApprovalMapper groundTaskCancelApprovalMapper;
    @Autowired
    private GroundEmployeeTaskMapper groundEmployeeTaskMapper;
    @Autowired
    private GroundEmployeeOrderMapper groundEmployeeOrderMapper;

    /**
     * 查询订单取消审批信息
     *
     * @param id 订单取消审批ID
     * @return 订单取消审批信息
     */
    @Override
    public TbTransactionOrderCancelApproval selectTbTransactionOrderCancelApprovalById(Integer id) {
        return tbTransactionOrderCancelApprovalMapper.selectTbTransactionOrderCancelApprovalById(id);
    }

    /**
     * 查询订单取消审批列表
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 订单取消审批集合
     */
    @Override
    public List<TbTransactionOrderCancelApproval> selectTbTransactionOrderCancelApprovalList(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        return tbTransactionOrderCancelApprovalMapper.selectTbTransactionOrderCancelApprovalList(tbTransactionOrderCancelApproval);
    }

    /**
     * 新增订单取消审批
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 结果
     */
    @Override
    public int insertTbTransactionOrderCancelApproval(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        return tbTransactionOrderCancelApprovalMapper.insertTbTransactionOrderCancelApproval(tbTransactionOrderCancelApproval);
    }

    /**
     * 修改订单取消审批
     *
     * @param tbTransactionOrderCancelApproval 订单取消审批信息
     * @return 结果
     */
    @Override
    public int updateTbTransactionOrderCancelApproval(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        return tbTransactionOrderCancelApprovalMapper.updateTbTransactionOrderCancelApproval(tbTransactionOrderCancelApproval);
    }

    /**
     * 删除订单取消审批对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTbTransactionOrderCancelApprovalByIds(String ids) {
        return tbTransactionOrderCancelApprovalMapper.deleteTbTransactionOrderCancelApprovalByIds(Convert.toStrArray(ids));
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
        List<TbTransactionOrderCancelApproval> orderCancelApprovalList = tbTransactionOrderCancelApprovalMapper.selectTbTransactionOrderCancelApprovalByIds(ids);
        for (TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval : orderCancelApprovalList) {
            orderIdList.add(tbTransactionOrderCancelApproval.getOrderId());
        }
        List<TbTransactionOrder> orderList = tbTransactionOrderMapper.selectTbTransactionOrderListByOrderIdList(orderIdList);
        int orderResult = 0;
        List<String> saleOrderIdList = new ArrayList<>();
        List<String> cancelOrderIdList = new ArrayList<>();
        List<String> removeTaskIdList = new ArrayList<>();
        List<Long> orderLongIdList = new ArrayList<>();
        for (TbTransactionOrder tbTransactionOrder : orderList) {
            orderLongIdList.add(tbTransactionOrder.getId());
            GroundTask groundTask = groundTaskMapper.selectGroundTaskByOrderId(tbTransactionOrder.getOrderId());
            if (groundTask == null) {
                orderResult++;
                continue;
            }
            if (tbTransactionOrder.getStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                saleOrderIdList.add(tbTransactionOrder.getOrderId());
            }
            if (tbTransactionOrder.getStatus().compareTo(TaskTaskStatus.CANCEL.getCode()) == 0) {
                cancelOrderIdList.add(tbTransactionOrder.getOrderId());
            }
            if (groundTask.getOrderNum() - 1 <= 0) {
                // 删除任务
                removeTaskIdList.add(groundTask.getTaskId());
                orderResult++;
            } else {
                GroundTask updateTask = new GroundTask();
                updateTask.setId(groundTask.getId());
                updateTask.setTaskId(groundTask.getTaskId());
                updateTask.setTaskCommissionPrice(groundTask.getTaskCommissionPrice() - tbTransactionOrder.getCommissionPrice());
                updateTask.setTaskCorpus(groundTask.getTaskCorpus() - tbTransactionOrder.getUnitPrice());
                updateTask.setOrderNum(groundTask.getOrderNum() - 1);
                orderResult += groundTaskMapper.updateGroundTask(updateTask);
            }
        }
        if (!removeTaskIdList.isEmpty()) {
            String[] removeTaskIdArray = removeTaskIdList.toArray(new String[removeTaskIdList.size()]);
            // 删除任务取消
            groundTaskCancelApprovalMapper.deleteGroundTaskCancelApprovalByTaskIdList(removeTaskIdList);
            // 删除业务员任务
            groundEmployeeTaskMapper.deleteByTaskIds(removeTaskIdArray);
            // 删除任务
            int removeTaskResult = groundTaskMapper.deleteGroundTaskByTaskIds(removeTaskIdArray);
            if (removeTaskResult != removeTaskIdList.size()) {
                throw new BusinessException("订单审核通过失败，删除关联任务失败");
            }
            // 删除关联任务时，需将订单待生成订单
            if (!orderLongIdList.isEmpty()) {
                // 更新相应订单状态为待生成任务订单
                tbTransactionOrderMapper.updateTbTransactionOrderAllocStatusInIds(orderLongIdList, TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                        , TbTransactionOrderAllocatStatus.COMPLETE.getCode());
                tbTransactionOrderMapper.updateTbTransactionOrderAllocStatusInIds(orderLongIdList, TbTransactionOrderAllocatStatus.UNFINISHED.getCode()
                        , TbTransactionOrderAllocatStatus.HISTORY.getCode());
            }
        }
        if (!cancelOrderIdList.isEmpty()) {
            // 删除订单取消申请
            int cancelOrderResult = tbTransactionOrderCancelApprovalMapper.deleteTbTransactionOrderCancelApprovalByOrderIdList(cancelOrderIdList);
            if (cancelOrderResult != cancelOrderIdList.size()) {
                throw new BusinessException("订单审核通过失败，删除关联取消订单申请失败");
            }
        }
        if (!saleOrderIdList.isEmpty()) {
            // 删除业务员接单
            int saleOrderResult = groundEmployeeOrderMapper.deleteGroundEmployeeOrderByOrderIdList(saleOrderIdList);
            if (saleOrderResult != saleOrderIdList.size()) {
                // throw new BusinessException("订单审核通过失败，删除关联销售订单申请失败");
                LogUtils.getAccessLog().info("订单审核通过失败，删除关联销售订单申请失败, {}", saleOrderIdList);
            }
        }
        // 更新状态为审核通过
        int count = tbTransactionOrderCancelApprovalMapper.pass(ids);
        if (count != orderResult) {
            LogUtils.getAccessLog().info("订单审核通过失败, 更新订单信息失败，{},{},{} ", ids, count, orderResult);
        }
        // 更新相应的订单状态为未接单
        tbTransactionOrderCancelApprovalMapper.updateTbTransactionOrderStatusByOrderIds(ids);
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
        int count = tbTransactionOrderCancelApprovalMapper.reject(orderIds);
        // 修改状态
        int result = tbTransactionOrderMapper.updateTbTransactionOrderStatusInCancelIds(Arrays.asList(orderIds), TbTransactionOrderStatus.COMPLETE.getCode(), TbTransactionOrderStatus.PLACEMENT.getCode());
        if (result <= 0) {
            throw new BusinessException("订单驳回失败，请重试");
        }
        return count;
    }

}
