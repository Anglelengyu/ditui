package net.hongkuang.ditui.project.busi.groundTaskOrder.service;

import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrder;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.SearchGroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.dto.GroundTaskOrderDto;

import java.util.List;

/**
 * 任务订单 服务层
 *
 * @author:zy
 * @date: 2019/4/17
 */
public interface IGroundTaskOrderService {
    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    public GroundTaskOrder selectGroundTaskOrderById(Long id);

    /**
     * 查询任务订单列表
     *
     * @param transactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    public List<GroundTaskOrder> selectGroundTaskOrderList(GroundTaskOrder transactionTaskOrder);

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrderDto 任务订单信息
     * @return 结果
     */
    public int insertGroundTaskOrder(GroundTaskOrderDto tbTransactionTaskOrderDto);

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int updateGroundTaskOrder(GroundTaskOrder tbTransactionTaskOrder);

    /**
     * 删除任务订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroundTaskOrderByIds(String taskId, String ids);

    /**
     * 查询任务订单列表
     *
     * @param searchGroundTaskOrderVo 任务订单信息
     * @return 任务订单集合
     */
    public List<GroundTaskOrderVo> selectGroundTaskOrderVoByTaskIds(SearchGroundTaskOrderVo searchGroundTaskOrderVo);


}
