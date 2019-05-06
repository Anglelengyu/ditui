package net.hongkuang.ditui.project.busi.groundTaskOrder.mapper;

import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrder;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.SearchGroundTaskOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务订单 数据层
 *
 * @author:zy
 * @date: 2019/4/17
 */
public interface GroundTaskOrderMapper {
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
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 任务订单集合
     */
    public List<GroundTaskOrder> selectGroundTaskOrderList(GroundTaskOrder tbTransactionTaskOrder);

    /**
     * 新增任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int insertGroundTaskOrder(GroundTaskOrder tbTransactionTaskOrder);

    /**
     * 修改任务订单
     *
     * @param tbTransactionTaskOrder 任务订单信息
     * @return 结果
     */
    public int updateGroundTaskOrder(GroundTaskOrder tbTransactionTaskOrder);

    int batchInsert(@Param("list") List<GroundTaskOrder> taskOrderList);

    List<GroundTaskOrder> selectGroundTaskOrderById(@Param("orderId") String orderId);

    List<GroundTaskOrder> selectGroundTaskOrderByOrderId(@Param("orderId") String orderId);

    /**
     * 删除任务订单
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroundTaskOrderById(@Param("taskId") String taskId, @Param("id") String id);

    List<String> selectGroundTaskOrderIdByTaskId(@Param("taskId") String taskId);

    List<String> selectGroundTaskOrderIdByTaskIds(@Param("ids") String[] taskIds);

    int deleteGroundTaskOrderByOrderIdList(@Param("list") List<String> orderIdList);

    /**
     * 查询订单
     *
     * @return 订单集合
     */
    List<GroundTaskOrderVo> selectGroundTaskOrderVoByTaskIds(SearchGroundTaskOrderVo searchGroundTaskOrderVo);

}