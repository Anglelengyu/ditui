package net.hongkuang.ditui.project.busi.groundTaskOrder.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.enums.GroundTaskStatus;
import net.hongkuang.ditui.project.busi.groundTask.mapper.GroundTaskMapper;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.SearchGroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.task.enums.TaskStatus;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrder;
import net.hongkuang.ditui.project.busi.groundTaskOrder.dto.GroundTaskOrderDto;
import net.hongkuang.ditui.project.busi.groundTaskOrder.mapper.GroundTaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 任务订单 服务层实现
 *
 * @author:zy
 * @date: 2019/4/17
 */
@Service
public class GroundTaskOrderServiceImpl implements IGroundTaskOrderService {
    @Autowired
    private GroundTaskOrderMapper groundTaskOrderMapper;
    @Autowired
    private GroundTaskMapper groundTaskMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;

    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    @Override
    public GroundTaskOrder selectGroundTaskOrderById(Long id) {
        return groundTaskOrderMapper.selectGroundTaskOrderById(id);
    }

    /**
     * 查询任务订单列表
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    @Override
    public List<GroundTaskOrder> selectGroundTaskOrderList(GroundTaskOrder tbTransactionTaskOrder) {
        return groundTaskOrderMapper.selectGroundTaskOrderList(tbTransactionTaskOrder);
    }

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrderDto 任务订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertGroundTaskOrder(GroundTaskOrderDto tbTransactionTaskOrderDto) {
        TbTransactionOrder tbTransactionOrder = tbTransactionOrderMapper.selectTbTransactionOrderByOrderId(tbTransactionTaskOrderDto.getOrderId());
        if (tbTransactionOrder == null || tbTransactionOrder.getStatus() != TbTransactionOrderStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前订单不存在");
        }
        GroundTask groundTask = groundTaskMapper.selectGroundTaskByTaskId(tbTransactionTaskOrderDto.getTaskId());
        if (groundTask == null || groundTask.getTaskStatus() != GroundTaskStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前任务不处于未接单状态");
        }
        List<GroundTaskOrder> orders = groundTaskOrderMapper.selectGroundTaskOrderByOrderId(tbTransactionOrder.getOrderId());
        if (orders != null && orders.size() > 0) {
            throw new BusinessException("该订单已分配，不可重复分配");
        }
        String operator = ShiroUtils.getLoginName();
        GroundTaskOrder taskOrder = new GroundTaskOrder();
        taskOrder.setOrderId(tbTransactionOrder.getOrderId());
        taskOrder.setCreateTime(new Date());
        taskOrder.setTaskId(groundTask.getTaskId());
        taskOrder.setCreateBy(operator);
        taskOrder.setTaskOrderId(tbTransactionOrder.getOrderId());
        taskOrder.setStatus(GroundTaskOrderStatus.NORMAL.getCode());
        taskOrder.setUpdateTime(new Date());
        taskOrder.setUpdateBy(operator);
        groundTaskOrderMapper.insertGroundTaskOrder(taskOrder);

        tbTransactionOrder.setAllocatStatus(TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
        tbTransactionOrderMapper.updateTbTransactionOrder(tbTransactionOrder);

        groundTask.setTaskCommissionPrice(groundTask.getTaskCommissionPrice() + tbTransactionOrder.getCommissionPrice());
        groundTask.setTaskCorpus(groundTask.getTaskCorpus() + tbTransactionOrder.getUnitPrice());
        groundTask.setOrderNum(groundTask.getOrderNum() + 1);

        return groundTaskMapper.updateGroundTask(groundTask);
    }

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    @Override
    public int updateGroundTaskOrder(GroundTaskOrder tbTransactionTaskOrder) {
        return groundTaskOrderMapper.updateGroundTaskOrder(tbTransactionTaskOrder);
    }

    /**
     * 删除任务订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
//	@Transactional
    public int deleteGroundTaskOrderByIds(String taskId, String ids) {
        GroundTask groundTask = groundTaskMapper.selectGroundTaskByTaskId(taskId);
        if (groundTask == null || groundTask.getTaskStatus() != TaskStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前任务不处于未接单状态");
        }
        int taskResult = 1;
        tbTransactionOrderMapper.selectTbTransactionOrderListByIds(Convert.toStrArray(ids)).forEach(tbTransactionOrder -> {
            if (tbTransactionOrder == null || tbTransactionOrder.getStatus() != TbTransactionOrderStatus.UNFINISHED.getCode()) {
                throw new BusinessException("当前订单不存在");
            }

            String operator = ShiroUtils.getLoginName();
            tbTransactionOrder.setAllocatStatus(TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
            tbTransactionOrder.setUpdateTime(new Date());
            tbTransactionOrder.setUpdateBy(operator);
            tbTransactionOrderMapper.updateTbTransactionOrder(tbTransactionOrder);

            groundTaskOrderMapper.deleteGroundTaskOrderById(groundTask.getTaskId(), tbTransactionOrder.getOrderId());
            groundTask.setTaskCommissionPrice(groundTask.getTaskCommissionPrice() - tbTransactionOrder.getCommissionPrice());
            groundTask.setTaskCorpus(groundTask.getTaskCorpus() - tbTransactionOrder.getUnitPrice());
            groundTask.setOrderNum(groundTask.getOrderNum() - 1);

            groundTaskMapper.updateGroundTask(groundTask);
        });
        return taskResult;
    }

    @Override
    public List<GroundTaskOrderVo> selectGroundTaskOrderVoByTaskIds(SearchGroundTaskOrderVo searchGroundTaskOrderVo) {
        if (!StringUtils.isEmpty(searchGroundTaskOrderVo.getStartTime())) {
            searchGroundTaskOrderVo.setStartTime(searchGroundTaskOrderVo.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchGroundTaskOrderVo.getEndTime())) {
            searchGroundTaskOrderVo.setEndTime(searchGroundTaskOrderVo.getEndTime() + " 23:59:59");
        }
        if (!StringUtils.isEmpty(searchGroundTaskOrderVo.getReferStartTime())) {
            searchGroundTaskOrderVo.setReferStartTime(searchGroundTaskOrderVo.getReferStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchGroundTaskOrderVo.getReferEndTime())) {
            searchGroundTaskOrderVo.setReferEndTime(searchGroundTaskOrderVo.getReferEndTime() + " 23:59:59");
        }
        return groundTaskOrderMapper.selectGroundTaskOrderVoByTaskIds(searchGroundTaskOrderVo);
    }
}
