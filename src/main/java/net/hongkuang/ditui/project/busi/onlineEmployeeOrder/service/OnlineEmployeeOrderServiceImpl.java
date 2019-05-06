package net.hongkuang.ditui.project.busi.onlineEmployeeOrder.service;

import java.util.List;

import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.domain.OnlineEmployeeOrder;
import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.mapper.OnlineEmployeeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.common.support.Convert;

/**
 * 业务员订单 服务层实现
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Service
public class OnlineEmployeeOrderServiceImpl implements IOnlineEmployeeOrderService {
    @Autowired
    private OnlineEmployeeOrderMapper onlineEmployeeOrderMapper;

    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    @Override
    public OnlineEmployeeOrder selectOnlineEmployeeOrderById(Long id) {
        return onlineEmployeeOrderMapper.selectOnlineEmployeeOrderById(id);
    }

    /**
     * 查询业务员订单列表
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    @Override
    public List<OnlineEmployeeOrder> selectOnlineEmployeeOrderList(OnlineEmployeeOrder onlineEmployeeOrder) {
        return onlineEmployeeOrderMapper.selectOnlineEmployeeOrderList(onlineEmployeeOrder);
    }

    /**
     * 新增业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int insertOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder) {
        return onlineEmployeeOrderMapper.insertOnlineEmployeeOrder(onlineEmployeeOrder);
    }

    /**
     * 修改业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int updateOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder) {
        return onlineEmployeeOrderMapper.updateOnlineEmployeeOrder(onlineEmployeeOrder);
    }

    /**
     * 删除业务员订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOnlineEmployeeOrderByIds(String ids) {
        return onlineEmployeeOrderMapper.deleteOnlineEmployeeOrderByIds(Convert.toStrArray(ids));
    }

}
