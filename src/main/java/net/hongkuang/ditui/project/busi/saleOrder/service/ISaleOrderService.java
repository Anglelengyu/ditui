package net.hongkuang.ditui.project.busi.saleOrder.service;

import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;

import java.util.List;

/**
 * 业务员订单 服务层
 *
 * @author yj
 * @date 2019-01-11
 */
public interface ISaleOrderService {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    public SaleOrder selectSaleOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param saleOrder 业务员订单信息
     * @return 业务员订单集合
     */
    public List<SaleOrder> selectSaleOrderList(SaleOrder saleOrder);

    /**
     * 新增业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    public int insertSaleOrder(SaleOrder saleOrder);

    /**
     * 修改业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    public int updateSaleOrder(SaleOrder saleOrder);

    /**
     * 删除业务员订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSaleOrderByIds(String ids);

}
