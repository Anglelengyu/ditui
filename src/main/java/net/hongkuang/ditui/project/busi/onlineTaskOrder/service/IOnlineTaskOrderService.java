package net.hongkuang.ditui.project.busi.onlineTaskOrder.service;

import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrder;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrderVo;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.SearchOnlineTaskOrderVo;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.dto.OnlineTaskOrderDto;

import java.util.List;

/**
 * 任务订单 服务层
 *
 * @author:zy
 * @date: 2019/4/17
 */
public interface IOnlineTaskOrderService {
    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    public OnlineTaskOrder selectOnlineTaskOrderById(Long id);

    /**
     * 查询任务订单列表
     *
     * @param transactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    public List<OnlineTaskOrder> selectOnlineTaskOrderList(OnlineTaskOrder transactionTaskOrder);

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrderDto 任务订单信息
     * @return 结果
     */
    public int insertOnlineTaskOrder(OnlineTaskOrderDto tbTransactionTaskOrderDto);

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int updateOnlineTaskOrder(OnlineTaskOrder tbTransactionTaskOrder);

    /**
     * 删除任务订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOnlineTaskOrderByIds(String taskId, String ids);

    /**
     * 查询任务订单列表
     *
     * @param searchOnlineTaskOrderVo 任务订单信息
     * @return 任务订单集合
     */
    public List<OnlineTaskOrderVo> selectOnlineTaskOrderVoByTaskIds(SearchOnlineTaskOrderVo searchOnlineTaskOrderVo);


}
