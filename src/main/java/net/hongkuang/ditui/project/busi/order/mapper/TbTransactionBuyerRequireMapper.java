package net.hongkuang.ditui.project.busi.order.mapper;

import net.hongkuang.ditui.project.busi.order.domain.TbTransactionBuyerRequire;
import org.springframework.stereotype.Repository;

/**
 * 淘宝买家要求 数据层
 *
 * @author:zy
 * @date: 2019/4/12
 */
@Repository
public interface TbTransactionBuyerRequireMapper {

    /**
     * 新增买家要求
     *
     * @param tbTransactionBuyerRequire 买家要求
     * @return 结果
     */
    int saveTbTransactionBuyerRequire(TbTransactionBuyerRequire tbTransactionBuyerRequire);

    /**
     * 删除订单问题
     *
     * @param id
     * @return 淘宝关键词信息
     */
    int deleteTbTransactionBuyerRequireByIds(String[] id);

}