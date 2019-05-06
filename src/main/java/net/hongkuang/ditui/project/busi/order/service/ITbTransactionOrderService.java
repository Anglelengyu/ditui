package net.hongkuang.ditui.project.busi.order.service;


import net.hongkuang.ditui.project.busi.order.domain.SearchTbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrderDto;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 淘宝订单 服务层
 *
 * @author:zy
 * @date: 2019/4/12
 */
public interface ITbTransactionOrderService {

    /**
     * 查询订单列表
     *
     * @param searchTbTransactionOrder 订单信息
     * @return 订单集合
     */
    public List<TbTransactionOrder> selectTbTransactionOrderList(SearchTbTransactionOrder searchTbTransactionOrder);

    /**
     * 查询单个订单
     *
     * @param id 订单id
     * @return 订单集合
     */
    public TbTransactionOrder selectTbTransactionOrderById(Long id);

    /**
     * 查询单个订单
     *
     * @param orderId 订单id
     * @return 订单集合
     */
    public TbTransactionOrder selectTbTransactionOrderByOrderId(String orderId);


    /**
     * 保存订单备注
     *
     * @param tbTransactionOrder 信息
     * @return 结果
     */
    int updateTbTransactionOrderRemark(TbTransactionOrder tbTransactionOrder);

    /**
     * 删除订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbTransactionOrderByIds(String ids);

    /**
     * 保存修改订单
     *
     * @param tbTransactionOrderDto 信息
     * @return 结果
     */
    int updateTbTransactionOrder(TbTransactionOrderDto tbTransactionOrderDto);

    int getGenType(Long teamId,String[] ids);

    int getOrderTotalNum(Long teamId,String[] ids);

    int genTask(GenTaskDto genTaskDto);

    /*
    * 获取任务数量
    * @param genType 生成方式
    * @return 结果
    */
    GenTaskDto getTaskNum(Integer genType,Long teamId,String[] ids);

    /**
     * 根据任务id查询订单
     *
     * @param taskId 任务id
     * @return 订单集合
     */
    List<TbTransactionOrder> selectTbTransactionOrderByTaskId(String taskId);

    boolean selectTbTransactionOrderIsDistribution(String[] ids);

    /**
     * 订单分配合作团队
     *
     * @param ids 信息
     * @return 结果
     */
    int distributionTeam(String[] ids, Long teamId, String teamName);


}
