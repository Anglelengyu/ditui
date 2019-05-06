package net.hongkuang.ditui.project.busi.groundEmployeeOrder.service;

import java.util.List;

import net.hongkuang.ditui.project.busi.groundEmployeeOrder.domain.GroundEmployeeOrder;
import net.hongkuang.ditui.project.busi.groundEmployeeOrder.mapper.GroundEmployeeOrderMapper;
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
public class GroundEmployeeOrderServiceImpl implements IGroundEmployeeOrderService {
    @Autowired
    private GroundEmployeeOrderMapper groundEmployeeOrderMapper;

    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    @Override
    public GroundEmployeeOrder selectGroundEmployeeOrderById(Long id) {
        return groundEmployeeOrderMapper.selectGroundEmployeeOrderById(id);
    }

    /**
     * 查询业务员订单列表
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    @Override
    public List<GroundEmployeeOrder> selectGroundEmployeeOrderList(GroundEmployeeOrder groundEmployeeOrder) {
        return groundEmployeeOrderMapper.selectGroundEmployeeOrderList(groundEmployeeOrder);
    }

    /**
     * 新增业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int insertGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder) {
        return groundEmployeeOrderMapper.insertGroundEmployeeOrder(groundEmployeeOrder);
    }

    /**
     * 修改业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    @Override
    public int updateGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder) {
        return groundEmployeeOrderMapper.updateGroundEmployeeOrder(groundEmployeeOrder);
    }

    /**
     * 删除业务员订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroundEmployeeOrderByIds(String ids) {
        return groundEmployeeOrderMapper.deleteGroundEmployeeOrderByIds(Convert.toStrArray(ids));
    }

}
