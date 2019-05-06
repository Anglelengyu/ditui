package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder;


import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;

import java.util.List;

/**
 * 淘宝问题订单 服务层
 *
 * @author:zy
 * @date: 2019/4/12
 */
public interface ITbTransactionQuestionOrderService {

    /**
     * 查询问题订单列表
     *
     * @param searchTbTransactionQuestionOrder 订单信息
     * @return 订单集合
     */
    public List<TbTransactionQuestionOrder> selectTbTransactionQuestionOrderList(SearchTbTransactionQuestionOrder searchTbTransactionQuestionOrder);

    /**
     * 保存问题订单列表
     *
     * @param ids 订单信息
     * @return 订单集合
     */
    int saveTbTransactionQuestionOrderList(String[] ids,String role,String questionOrderRemark);

    /**
     * 查询问题订单信息
     *
     * @param id 问题订单ID
     * @return 任务订单信息
     */
    public TbTransactionQuestionOrder selectTbTransactionQuestionOrderById(Long id);

    /**
     * 保存订单状态
     *
     * @param tbTransactionQuestionOrder 信息
     * @return 结果
     */
    int updateTbTransactionQuestionOrder(TbTransactionQuestionOrder tbTransactionQuestionOrder);

}
