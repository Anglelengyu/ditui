package net.hongkuang.ditui.project.busi.orderTemplate.mapper;

import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateKeyWords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 订单模版关键词 数据层
 *
 * @author:zy
 * @date: 2019/2/26
 */
@Repository
public interface OrderTemplateKeyWordsMapper {

    /**
     * 新增关键词
     *
     * @param orderTemplateKeyWords 关键词信息
     * @return 结果
     */
    int insertOrderTemplateKeyWords(OrderTemplateKeyWords orderTemplateKeyWords);

    /**
     * 查询关键词列表
     *
     * @param id 模版id
     * @return 结果
     */
    List<OrderTemplateKeyWords> selectOrderTemplateKeyWordsList(Long id);

    /**
     * 根据模版id删除关键词
     *
     * @param id 模版id
     * @return 结果
     */
    int deleteOrderTemplateKeyWordsByTemplateId(Long id);

    /**
     * 根据模版ids删除关键词
     *
     * @param ids 模版ids
     * @return 结果
     */
    int deleteOrderTemplateKeyWordsByTemplateIds(String[] ids);

    /**
     * 根据模版id根据关键词数量
     *
     * @param orderTemplateId 模版id
     * @return 结果
     */
    int getOrderTemplateKeyWordsCount(@Param("orderTemplateId") Long orderTemplateId);

    int deleteOrderTemplateKeyWords(@Param("orderTemplateKeyWordsList") List<OrderTemplateKeyWords> orderTemplateKeyWords);

    int updateOrderTemplateKeyWords(OrderTemplateKeyWords orderTemplateKeyWords);

}