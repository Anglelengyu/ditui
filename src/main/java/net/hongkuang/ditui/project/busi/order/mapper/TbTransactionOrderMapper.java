package net.hongkuang.ditui.project.busi.order.mapper;

import net.hongkuang.ditui.project.busi.order.domain.SearchTbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 淘宝订单 数据层
 *
 * @author:zy
 * @date: 2019/4/12
 */
@Repository
public interface TbTransactionOrderMapper {

    /**
     * 查询订单列表
     *
     * @param searchTbTransactionOrder 订单信息
     * @return 订单集合
     */
    List<TbTransactionOrder> selectTbTransactionOrderList(SearchTbTransactionOrder searchTbTransactionOrder);

    /**
     * 查询单个订单
     *
     * @param id 订单id
     * @return 订单集合
     */
    TbTransactionOrder selectTbTransactionOrderById(Long id);

    /**
     * 查询单个订单
     *
     * @param orderId 订单id
     * @return 订单集合
     */
    TbTransactionOrder selectTbTransactionOrderByOrderId(String orderId);

    /**
     * 更新订单备注
     *
     * @param tbTransactionOrder
     * @return 淘宝信息
     */
    int updateTbTransactionOrderRemark(TbTransactionOrder tbTransactionOrder);

    /**
     * 更新订单
     *
     * @param tbTransactionOrder
     * @return 淘宝信息
     */
    int updateTbTransactionOrder(TbTransactionOrder tbTransactionOrder);

    /**
     * 更新订单 问题订单ID
     *
     * @param tbTransactionOrder
     * @return 淘宝信息
     */
    int updateTbTransactionOrderForQuestionOrderId(TbTransactionOrder tbTransactionOrder);

    /**
     * 分配给合作团队
     *
     * @param ids
     * @return 结果
     */
    int distributionTeam(@Param("ids") String[] ids, @Param("receiptTeamId") Long receiptTeamId, @Param("receiptTeamName") String receiptTeamName);

    /**
     * 新增淘宝订单
     *
     * @param tbTransactionOrder 订单信息
     * @return 结果
     */
    int saveTbTransactionOrder(TbTransactionOrder tbTransactionOrder);

    int saveBatchTbTransactionOrder(@Param("list") List<TbTransactionOrder> batchOrder);

    /**
     * 删除订单
     *
     * @param ids
     * @return 淘宝模版信息
     */
    int deleteTbTransactionOrderByIds(String[] ids);

    /**
     * 删除订单
     *
     * @param ids
     * @return 淘宝模版信息
     */
    int deleteTbTransactionOrderByOrderIds(String[] ids);

    int getGroupClassifysCount(@Param("allocatStatus") Integer allocatStatus, @Param("teamId") Long teamId, @Param("ids") String[] ids);

    int getOrderTotalNum(@Param("allocatStatus") Integer allocatStatus, @Param("teamId") Long teamId, @Param("ids") String[] ids);

    int getGroupClassifysCountForReckonAllocatStatus(@Param("reckonAllocatStatus") Integer reckonAllocatStatus, @Param("teamId") Long teamId,@Param("ids") String[] ids);

    List<TbTransactionOrder> selectTbTrasactionWaitBuildList(SearchTbTransactionOrder searchTbTransactionOrder);

    int updateTbTransactionOrderReckonAllocatStatus(@Param("list") List<Long> orderIdList, @Param("reckonAllocatStatus") Integer reckonAllocatStatus);

    List<TbTransactionWaitBuildGoodsDto> getUnFinishGoodsNameList(@Param("status") Integer status, @Param("teamId")Long teamId);

    List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysListForReckonAllocatStatus(@Param("reckonAllocatStatus") Integer reckonAllocatStatus, @Param("teamId") Long teamId,@Param("ids") String[] ids);

    List<TbTransactionWaitBuildGoodsDto> getWaitBuildGoodsReckonAllocatstatusList(@Param("status") Integer status, @Param("groupClassifys") String groupClassifys
            , @Param("teamId") Long teamId,@Param("ids") String[] ids);

    int updateTbTransactionOrderAllocStatusInIdsForReckonAllocatStatus(@Param("list") List<Long> orderIdList, @Param("reckonAllocatStatus") Integer reckonAllocatStatus
            , @Param("preReckonAllocatStatus") Integer preReckonAllocatStatus);

    int updateTbTransactionOrderAllocStatusInIds(@Param("list") List<Long> orderIdList, @Param("orderStatus") Integer updateOrderStatus
            , @Param("preOrderStatus") Integer preOrderStatus);

    List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysList(@Param("status") Integer status, @Param("teamId") Long teamId);

    /**
     * 根据任务id查询单个订单
     *
     * @param taskId 任务id
     * @return 订单集合
     */
    List<TbTransactionOrder> selectTbTransactionOrderByTaskId(String taskId);

    List<TbTransactionOrder> selectTbTransactionOrderListByIds(String[] ids);

    List<TbTransactionOrder> selectTbTransactionOrderListByOrderIdList(@Param("list") List<String> orderIdList);

    List<TbTransactionWaitBuildGoodsDto> getAutomaticGoodsList(@Param("taskStatus") Integer taskStatus,@Param("ids")Long[] ids);

    int updateTbTransactionOrderStatusInOrderIds(@Param("list") List<String> orderIdList, @Param("orderStatus") Integer updateOrderStatus
            , @Param("preOrderStatus") Integer preOrderStatus);

    int updateTbTransactionOrderAllocatStatusInOrderIds(@Param("list") List<String> orderIdList, @Param("allocatStatus") Integer allocatStatus
            , @Param("preAllocatStatus") Integer preAllocatStatus);

    int updateTbTransactionOrderAllocatStatusInTaskIds(@Param("taskIds") Long[] taskIds, @Param("allocatStatus") Integer allocatStatus
            , @Param("preAllocatStatus") Integer preAllocatStatus);

    TbTransactionOrder checkTbTransactionOrderStatusByEmployeeId(@Param("employeeId") Long employeeId, @Param("status") Integer status);

    /**
     * 根据任务id查询订单
     *
     * @param taskIds 任务ids
     * @return 订单集合
     */
    List<TbTransactionOrder> selectTbTransactionOrderByTaskIds(String[] taskIds);

    List<TbTransactionOrder> selectTbTransactionOrderListByOrderIds(String[] orderIds);

    /**
     * 任务分配给合作团队
     *
     * @param ids
     * @return 结果
     */
    int taskDistributionTeam(@Param("list") List<Long> ids, @Param("receiptTeamId") Long receiptTeamId, @Param("receiptTeamName") String receiptTeamName);

    int updateTbTransactionOrderStatusInCancelIds(@Param("cancelIds") List<String> asList, @Param("status") Integer status, @Param("preStatus") Integer preStatus);

}