package net.hongkuang.ditui.project.busi.order.mapper;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
import net.hongkuang.ditui.project.busi.order.dto.UnfinishGoodsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单 数据层
 *
 * @author yj
 * @date 2018-12-30
 */
@Repository
public interface OrderMapper {
    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    Order selectOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    List<Order> selectOrderList(SearchOrder order);

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    int deleteOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderByIds(String[] ids);

    int insertBatchOrder(@Param("list") List<Order> batchOrder);

    int getGoodsCategoryCount(@Param("status") Integer status, @Param("shopIdList") List<String> shopIdList);

    List<UnfinishGoodsDto> getUnFinishGoodsCategoryList(@Param("status") Integer status, @Param("shopIdList") List<String> shopIdList);

    List<Order> selectUnFinishedList(SearchOrder searchOrder);

    int updateOrderAllocStatusInIds(@Param("list") List<Long> orderIdList, @Param("orderStatus") Integer updateOrderStatus
            , @Param("preOrderStatus") Integer preOrderStatus);

    List<Order> selectOrderListByOrderId(String taskId);

    int migrationOrderByIds(String[] ids);

    List<UnfinishGoodsDto> getAutomaticGoodsList(@Param("taskStatus") Integer taskStatus);

    /**
     * 更新带那个单状态为未接单
     *
     * @param taskId
     * @return
     */
    int updateOrderStatusByTaskIds(String[] taskId);

    Order selectOrderByOrderId(@Param("orderId") String orderId);

    int updateOrderStatusInIds(@Param("list") List<Long> orderIdList, @Param("orderStatus") Integer updateOrderStatus
            , @Param("preOrderStatus") Integer preOrderStatus);

    int updateOrderStatusInOrderIds(@Param("list") List<String> orderIdList, @Param("orderStatus") Integer updateOrderStatus
            , @Param("preOrderStatus") Integer preOrderStatus);

    Order checkOrderStatusBySaleId(@Param("saleId") String saleId, @Param("status") Integer status);

    List<Order> selectExtendOrderList(SearchOrder order);

    List<Order> selectOrderListByIds(String[] ids);

    List<UnfinishGoodsDto> getUnFinishGoodsList(@Param("status") Integer status, @Param("category") String category
            , @Param("shopIdList") List<String> shopIdList);

    void updateOrderStatusAndOrderNo(List<Order> orders);

    List<Long> selectIdsByOrderIds(String[] orderIds);

    int updateOrderStatusInCancelIds(@Param("cancelIds") List<String> asList, @Param("status") Integer status, @Param("preStatus") Integer preStatus);

    List<Order> selectOrderListByOrderIdList(@Param("list") List<String> orderIdList);

    int getGoodsCategoryCountForReckonAllocatStatus(@Param("reckonAllocatStatus") Integer reckonAllocatStatus, @Param("shopIdList") List<String> shopIdList);

    List<UnfinishGoodsDto> getUnFinishGoodsCategoryListForReckonAllocatStatus(@Param("reckonAllocatStatus") Integer reckonAllocatStatus, @Param("shopIdList") List<String> shopIdList);

    int updateOrderAllocStatusInIdsForReckonAllocatStatus(@Param("list") List<Long> orderIdList, @Param("reckonAllocatStatus") Integer reckonAllocatStatus
            , @Param("preReckonAllocatStatus") Integer preReckonAllocatStatus);

    int updateOrderReckonAllocatStatus(@Param("list") List<Long> orderIdList, @Param("reckonAllocatStatus") Integer reckonAllocatStatus);

    List<UnfinishGoodsDto> getUnFinishGoodsReckonAllocatstatusList(@Param("status") Integer status, @Param("category") String category
            , @Param("shopIdList") List<String> shopIdList);
}