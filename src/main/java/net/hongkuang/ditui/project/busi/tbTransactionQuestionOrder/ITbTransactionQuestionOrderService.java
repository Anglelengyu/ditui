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
     * @param orderIds 订单信息
     * @return 订单集合
     */
    int saveTbTransactionQuestionOrderList(String[] orderIds,String questionOrderRemark);



}
