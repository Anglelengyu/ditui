package net.hongkuang.ditui.project.busi.onlineEmployeeOrder.service;

import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.domain.OnlineEmployeeOrder;

import java.util.List;

/**
 * 业务员订单 服务层
 *
 * @author:zy
 * @date: 2019/4/19
 */
public interface IOnlineEmployeeOrderService {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    public OnlineEmployeeOrder selectOnlineEmployeeOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    public List<OnlineEmployeeOrder> selectOnlineEmployeeOrderList(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 新增业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    public int insertOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 修改业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    public int updateOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 删除业务员订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOnlineEmployeeOrderByIds(String ids);

}
