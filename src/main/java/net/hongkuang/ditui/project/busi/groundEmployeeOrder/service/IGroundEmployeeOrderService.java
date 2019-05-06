package net.hongkuang.ditui.project.busi.groundEmployeeOrder.service;

import net.hongkuang.ditui.project.busi.groundEmployeeOrder.domain.GroundEmployeeOrder;

import java.util.List;

/**
 * 业务员订单 服务层
 *
 * @author:zy
 * @date: 2019/4/19
 */
public interface IGroundEmployeeOrderService {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    public GroundEmployeeOrder selectGroundEmployeeOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    public List<GroundEmployeeOrder> selectGroundEmployeeOrderList(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 新增业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    public int insertGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 修改业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    public int updateGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 删除业务员订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroundEmployeeOrderByIds(String ids);

}
