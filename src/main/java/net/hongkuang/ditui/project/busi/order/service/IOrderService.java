package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.ImportOrderDto;

import java.util.List;

/**
 * 订单 服务层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface IOrderService {
    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    public Order selectOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    public List<Order> selectOrderList(SearchOrder order);

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderByIds(String ids);

    /**
     * 导入订单
     *
     * @param importOrderDto 导入订单信息
     * @return
     */
    int importExcel(ImportOrderDto importOrderDto);

    int genTask(GenTaskDto genTaskDto);

    int getGenType();

    /**
     * 根据任务id获取相关任务
     *
     * @param taskId
     * @return
     */
    List<Order> selectOrderListByOrderId(String taskId);

    int migrationOrderByIds(String ids);

    List<String> getShopIdList(Long shopManagerId, String shopId);

    /*
    * 获取任务数量
    * @param genType 生成方式
    * @return 结果
    */
    GenTaskDto getTaskNum(Integer genType);
}
