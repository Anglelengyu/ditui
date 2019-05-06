package net.hongkuang.ditui.project.busi.saleOrder.mapper;

import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务员订单 数据层
 *
 * @author yj
 * @date 2019-01-11
 */
@Repository
public interface SaleOrderMapper {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    SaleOrder selectSaleOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param saleOrder 业务员订单信息
     * @return 业务员订单集合
     */
    List<SaleOrder> selectSaleOrderList(SaleOrder saleOrder);

    /**
     * 新增业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    int insertSaleOrder(SaleOrder saleOrder);

    /**
     * 修改业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    int updateSaleOrder(SaleOrder saleOrder);

    /**
     * 删除业务员订单
     *
     * @param id 业务员订单ID
     * @return 结果
     */
    int deleteSaleOrderById(Long id);

    /**
     * 批量删除业务员订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSaleOrderByIds(String[] ids);

    int updateSaleOrderStatusByTaskId(String[] taskId);

    int batchInsert(@Param("list") List<SaleOrder> saleOrderList);

    int deleteSaleOrderByOrderIdList(@Param("list") List<String> orderIdList);
}