package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.mapper;

import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 淘宝订单 数据层
 *
 * @author:zy
 * @date: 2019/4/12
 */
@Repository
public interface TbTransactionQuestionOrderMapper {

    /**
     * 查询订单列表
     *
     * @param searchTbTransactionQuestionOrder 订单信息
     * @return 订单集合
     */
    List<TbTransactionQuestionOrder> selectTbTransactionQuestionOrderList(SearchTbTransactionQuestionOrder searchTbTransactionQuestionOrder);

    int saveBatchTbTransactionQuestionOrder(@Param("list") List<TbTransactionQuestionOrder> batchQuestionOrder);

    /**
     * 查询问题订单信息
     *
     * @param id 问题订单ID
     * @return 任务订单信息
     */
    public TbTransactionQuestionOrder selectTbTransactionQuestionOrderById(Long id);

    int updateTbTransactionQuestionOrder(TbTransactionQuestionOrder tbTransactionQuestionOrder);


}