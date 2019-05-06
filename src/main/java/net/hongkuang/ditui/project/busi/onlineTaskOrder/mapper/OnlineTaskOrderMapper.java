package net.hongkuang.ditui.project.busi.onlineTaskOrder.mapper;

import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrder;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.OnlineTaskOrderVo;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.domain.SearchOnlineTaskOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务订单 数据层
 *
 * @author:zy
 * @date: 2019/4/17
 */
@Repository
public interface OnlineTaskOrderMapper {
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
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    public List<OnlineTaskOrder> selectOnlineTaskOrderList(OnlineTaskOrder tbTransactionTaskOrder);

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int insertOnlineTaskOrder(OnlineTaskOrder tbTransactionTaskOrder);

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int updateOnlineTaskOrder(OnlineTaskOrder tbTransactionTaskOrder);

    int batchInsert(@Param("list") List<OnlineTaskOrder> taskOrderList);

    List<OnlineTaskOrder> selectOnlineTaskOrderById(@Param("orderId") String orderId);

    List<OnlineTaskOrder> selectOnlineTaskOrderByOrderId(@Param("orderId") String orderId);

    /**
     * 删除任务订单
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteOnlineTaskOrderById(@Param("taskId") String taskId, @Param("id") String id);

    List<String> selectOnlineTaskOrderIdByTaskId(@Param("taskId") String taskId);

    List<String> selectOnlineTaskOrderIdByTaskIds(@Param("ids") String[] taskIds);

    int deleteOnlineTaskOrderByOrderIdList(@Param("list") List<String> orderIdList);

    /**
     * 查询订单
     *
     * @return 订单集合
     */
    List<OnlineTaskOrderVo> selectOnlineTaskOrderVoByTaskIds(SearchOnlineTaskOrderVo searchOnlineTaskOrderVo);

}