package net.hongkuang.ditui.project.busi.orderTemplate.mapper;

import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.SearchOrderTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单模版 数据层
 *
 * @author:zy
 * @date: 2019/2/25
 */
@Repository
public interface OrderTemplateMapper {
    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    OrderTemplate selectOrderTemplateById(Long id);

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    List<OrderTemplate> selectOrderTemplateList(SearchOrderTemplate order);

    /**
     * 新增订单
     *
     * @param orderTemplate 订单信息
     * @return 结果
     */
    int insertOrderTemplate(OrderTemplate orderTemplate);

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    int updateOrderTemplate(OrderTemplate order);

    /**
     * 删除订单
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderTemplateById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOrderTemplateByIds(String[] ids);

    /**
     * 修改订单
     *
     * @param status 订单信息
     * @return 结果
     */
    int updateOrderTemplateStatus(@Param("ids") String[] ids, @Param("status") Integer status);


}