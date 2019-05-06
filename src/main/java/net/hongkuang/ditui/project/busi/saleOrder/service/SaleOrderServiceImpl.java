package net.hongkuang.ditui.project.busi.saleOrder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import net.hongkuang.ditui.project.busi.saleOrder.service.ISaleOrderService;
import net.hongkuang.ditui.common.support.Convert;

/**
 * 业务员订单 服务层实现
 *
 * @author yj
 * @date 2019-01-11
 */
@Service
public class SaleOrderServiceImpl implements ISaleOrderService {
    @Autowired
    private SaleOrderMapper saleOrderMapper;

    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    @Override
    public SaleOrder selectSaleOrderById(Long id) {
        return saleOrderMapper.selectSaleOrderById(id);
    }

    /**
     * 查询业务员订单列表
     *
     * @param saleOrder 业务员订单信息
     * @return 业务员订单集合
     */
    @Override
    public List<SaleOrder> selectSaleOrderList(SaleOrder saleOrder) {
        return saleOrderMapper.selectSaleOrderList(saleOrder);
    }

    /**
     * 新增业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int insertSaleOrder(SaleOrder saleOrder) {
        return saleOrderMapper.insertSaleOrder(saleOrder);
    }

    /**
     * 修改业务员订单
     *
     * @param saleOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int updateSaleOrder(SaleOrder saleOrder) {
        return saleOrderMapper.updateSaleOrder(saleOrder);
    }

    /**
     * 删除业务员订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSaleOrderByIds(String ids) {
        return saleOrderMapper.deleteSaleOrderByIds(Convert.toStrArray(ids));
    }

}
