package net.hongkuang.ditui.project.busi.onlineEmployeeOrder.mapper;

import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.domain.OnlineEmployeeOrder;
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
public interface OnlineEmployeeOrderMapper {
    /**
     * 查询业务员订单信息
     *
     * @param id 业务员订单ID
     * @return 业务员订单信息
     */
    OnlineEmployeeOrder selectOnlineEmployeeOrderById(Long id);

    /**
     * 查询业务员订单列表
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 业务员订单集合
     */
    List<OnlineEmployeeOrder> selectOnlineEmployeeOrderList(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 新增业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    int insertOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 修改业务员订单
     *
     * @param onlineEmployeeOrder 业务员订单信息
     * @return 结果
     */
    int updateOnlineEmployeeOrder(OnlineEmployeeOrder onlineEmployeeOrder);

    /**
     * 删除业务员订单
     *
     * @param id 业务员订单ID
     * @return 结果
     */
    int deleteOnlineEmployeeOrderById(Long id);

    /**
     * 批量删除业务员订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineEmployeeOrderByIds(String[] ids);

    int updateOnlineEmployeeOrderStatusByTaskId(String[] taskId);

    int batchInsert(@Param("list") List<OnlineEmployeeOrder> onlineEmployeeOrderList);

    int deleteOnlineEmployeeOrderByOrderIdList(@Param("list") List<String> orderIdList);
}