package net.hongkuang.ditui.project.busi.onlineTaskOrder.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.enums.OnlineTaskStatus;
import net.hongkuang.ditui.project.busi.onlineTask.mapper.OnlineTaskMapper;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrderVo;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.SearchOnlineTaskOrderVo;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.task.enums.TaskStatus;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrder;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.dto.OnlineTaskOrderDto;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.mapper.OnlineTaskOrderMapper;
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
public class OnlineTaskOrderServiceImpl implements IOnlineTaskOrderService {
    @Autowired
    private OnlineTaskOrderMapper onlineTaskOrderMapper;
    @Autowired
    private OnlineTaskMapper onlineTaskMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;

    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    @Override
    public OnlineTaskOrder selectOnlineTaskOrderById(Long id) {
        return onlineTaskOrderMapper.selectOnlineTaskOrderById(id);
    }

    /**
     * 查询任务订单列表
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    @Override
    public List<OnlineTaskOrder> selectOnlineTaskOrderList(OnlineTaskOrder tbTransactionTaskOrder) {
        return onlineTaskOrderMapper.selectOnlineTaskOrderList(tbTransactionTaskOrder);
    }

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrderDto 任务订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertOnlineTaskOrder(OnlineTaskOrderDto tbTransactionTaskOrderDto) {
        TbTransactionOrder tbTransactionOrder = tbTransactionOrderMapper.selectTbTransactionOrderByOrderId(tbTransactionTaskOrderDto.getOrderId());
        if (tbTransactionOrder == null || tbTransactionOrder.getStatus() != TbTransactionOrderStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前订单不存在");
        }
        OnlineTask onlineTask = onlineTaskMapper.selectOnlineTaskByTaskId(tbTransactionTaskOrderDto.getTaskId());
        if (onlineTask == null || onlineTask.getTaskStatus() != OnlineTaskStatus.UNFINISHED.getCode()) {
            throw new BusinessException("当前任务不处于未接单状态");
        }
        List<OnlineTaskOrder> orders = onlineTaskOrderMapper.selectOnlineTaskOrderByOrderId(tbTransactionOrder.getOrderId());
        if (orders != null && orders.size() > 0) {
            throw new BusinessException("该订单已分配，不可重复分配");
        }
        String operator = ShiroUtils.getLoginName();
        OnlineTaskOrder taskOrder = new OnlineTaskOrder();
        taskOrder.setOrderId(tbTransactionOrder.getOrderId());
        taskOrder.setCreateTime(new Date());
        taskOrder.setTaskId(onlineTask.getTaskId());
        taskOrder.setCreateBy(operator);
        taskOrder.setTaskOrderId(tbTransactionOrder.getOrderId());
        taskOrder.setStatus(OnlineTaskOrderStatus.NORMAL.getCode());
        taskOrder.setUpdateTime(new Date());
        taskOrder.setUpdateBy(operator);
        onlineTaskOrderMapper.insertOnlineTaskOrder(taskOrder);

        tbTransactionOrder.setAllocatStatus(TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
        tbTransactionOrderMapper.updateTbTransactionOrder(tbTransactionOrder);

        onlineTask.setTaskCommissionPrice(onlineTask.getTaskCommissionPrice() + tbTransactionOrder.getCommissionPrice());
        onlineTask.setTaskCorpus(onlineTask.getTaskCorpus() + tbTransactionOrder.getUnitPrice());
        onlineTask.setOrderNum(onlineTask.getOrderNum() + 1);

        return onlineTaskMapper.updateOnlineTask(onlineTask);
    }

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    @Override
    public int updateOnlineTaskOrder(OnlineTaskOrder tbTransactionTaskOrder) {
        return onlineTaskOrderMapper.updateOnlineTaskOrder(tbTransactionTaskOrder);
    }

    /**
     * 删除任务订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
//	@Transactional
    public int deleteOnlineTaskOrderByIds(String taskId, String ids) {
        OnlineTask onlineTask = onlineTaskMapper.selectOnlineTaskByTaskId(taskId);
        if (onlineTask == null || onlineTask.getTaskStatus() != TaskStatus.UNFINISHED.getCode()) {
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

            onlineTaskOrderMapper.deleteOnlineTaskOrderById(onlineTask.getTaskId(), tbTransactionOrder.getOrderId());
            onlineTask.setTaskCommissionPrice(onlineTask.getTaskCommissionPrice() - tbTransactionOrder.getCommissionPrice());
            onlineTask.setTaskCorpus(onlineTask.getTaskCorpus() - tbTransactionOrder.getUnitPrice());
            onlineTask.setOrderNum(onlineTask.getOrderNum() - 1);

            onlineTaskMapper.updateOnlineTask(onlineTask);
        });
        return taskResult;
    }

    @Override
    public List<OnlineTaskOrderVo> selectOnlineTaskOrderVoByTaskIds(SearchOnlineTaskOrderVo searchOnlineTaskOrderVo) {
        if (!StringUtils.isEmpty(searchOnlineTaskOrderVo.getStartTime())) {
            searchOnlineTaskOrderVo.setStartTime(searchOnlineTaskOrderVo.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchOnlineTaskOrderVo.getEndTime())) {
            searchOnlineTaskOrderVo.setEndTime(searchOnlineTaskOrderVo.getEndTime() + " 23:59:59");
        }
        if (!StringUtils.isEmpty(searchOnlineTaskOrderVo.getReferStartTime())) {
            searchOnlineTaskOrderVo.setReferStartTime(searchOnlineTaskOrderVo.getReferStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchOnlineTaskOrderVo.getReferEndTime())) {
            searchOnlineTaskOrderVo.setReferEndTime(searchOnlineTaskOrderVo.getReferEndTime() + " 23:59:59");
        }
        return onlineTaskOrderMapper.selectOnlineTaskOrderVoByTaskIds(searchOnlineTaskOrderVo);
    }
}
