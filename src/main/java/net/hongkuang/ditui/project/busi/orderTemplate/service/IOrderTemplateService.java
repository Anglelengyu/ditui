package net.hongkuang.ditui.project.busi.orderTemplate.service;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateDto;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.SearchOrderTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单模版 服务层
 *
 * @author:zy
 * @date: 2019/2/25
 */
public interface IOrderTemplateService {
    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    public OrderTemplate selectOrderTemplateById(Long id);

    /**
     * 查询订单列表
     *
     * @param searchOrderTemplate 订单信息
     * @return 订单集合
     */
    public List<OrderTemplate> selectOrderTemplateList(SearchOrderTemplate searchOrderTemplate);

    /**
     * 新增订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    public int insertOrderTemplate(OrderTemplateDto orderDto);

    /**
     * 修改模版
     *
     * @param orderDto 模版信息
     * @return 结果
     */
    public int updateOrderTemplate(OrderTemplateDto orderDto);

    /**
     * 删除模版信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderTemplateByIds(String ids);

    /**
     * 提交订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    public int referOrderTemplate(OrderTemplateDto orderDto);

    /**
     * 审核订单
     *
     * @param status 订单信息
     * @return 结果
     */
    public int executeCheck(@Param("ids") String ids, @Param("status") Integer status);

    /**
     * 重新提交订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    public int againReferOrderTemplate(OrderTemplateDto orderDto);

    /**
     * 拆分订单
     *
     * @param num 拆分数量
     * @return 结果
     */
    public int split(Long id, Integer num);
}
