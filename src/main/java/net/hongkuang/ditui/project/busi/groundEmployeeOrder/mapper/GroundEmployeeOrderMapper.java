package net.hongkuang.ditui.project.busi.groundEmployeeOrder.mapper;

import net.hongkuang.ditui.project.busi.groundEmployeeOrder.domain.GroundEmployeeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务员订单 数据层
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Repository
public interface GroundEmployeeOrderMapper {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    GroundEmployeeOrder selectGroundEmployeeOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    List<GroundEmployeeOrder> selectGroundEmployeeOrderList(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 新增业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    int insertGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 修改业务员订单
     *
     * @param groundEmployeeOrder 业务员订单信息
     * @return 结果
     */
    int updateGroundEmployeeOrder(GroundEmployeeOrder groundEmployeeOrder);

    /**
     * 删除业务员订单
     *
     * @param id 业务员订单ID
     * @return 结果
     */
    int deleteGroundEmployeeOrderById(Long id);

    /**
     * 批量删除业务员订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundEmployeeOrderByIds(String[] ids);

    int updateGroundEmployeeOrderStatusByTaskId(String[] taskId);

    int batchInsert(@Param("list") List<GroundEmployeeOrder> groundEmployeeOrderList);

    int deleteGroundEmployeeOrderByOrderIdList(@Param("list") List<String> orderIdList);
}