package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.mapper.TbTransactionQuestionOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 淘宝问题订单 服务层实现
 *
 * @author:zy
 * @date: 2019/4/26
 */
@Service
public class TbTransactionQuestionOrderServiceImpl implements ITbTransactionQuestionOrderService {

    @Autowired
    private TbTransactionQuestionOrderMapper tbTransactionQuestionOrderMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;


    @Override
    public List<TbTransactionQuestionOrder> selectTbTransactionQuestionOrderList(SearchTbTransactionQuestionOrder searchTbTransactionQuestionOrder) {

        if (!StringUtils.isEmpty(searchTbTransactionQuestionOrder.getStartTime())) {
            searchTbTransactionQuestionOrder.setStartTime(searchTbTransactionQuestionOrder.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchTbTransactionQuestionOrder.getEndTime())) {
            searchTbTransactionQuestionOrder.setEndTime(searchTbTransactionQuestionOrder.getEndTime() + " 23:59:59");
        }
        return tbTransactionQuestionOrderMapper.selectTbTransactionQuestionOrderList(searchTbTransactionQuestionOrder);
    }

    @Override
    @Transactional
    public int saveTbTransactionQuestionOrderList(String[] ids,String role,String questionOrderRemark) {
        List<TbTransactionQuestionOrder> tbTransactionQuestionOrderList = new ArrayList<>();
        tbTransactionOrderMapper.selectTbTransactionOrderListByIds(ids).forEach(tbTransactionOrder -> {
            TbTransactionQuestionOrder tbTransactionQuestionOrder = new TbTransactionQuestionOrder();
            tbTransactionQuestionOrder.setOrderId(tbTransactionOrder.getOrderId());
            tbTransactionQuestionOrder.setQuestionOrderRemark(questionOrderRemark);
            tbTransactionQuestionOrder.setQuestionOrderRole(role);
            tbTransactionQuestionOrder.setQuestionOrderStatus(0);
            tbTransactionQuestionOrderList.add(tbTransactionQuestionOrder);
        });
        int i = tbTransactionQuestionOrderMapper.saveBatchTbTransactionQuestionOrder(tbTransactionQuestionOrderList);
        tbTransactionQuestionOrderList.forEach(tbTransactionQuestionOrder -> {
            TbTransactionOrder tbTransactionOrder = new TbTransactionOrder();
            tbTransactionOrder.setOrderId(tbTransactionQuestionOrder.getOrderId());
            tbTransactionOrder.setQuestionOrderId(tbTransactionQuestionOrder.getQuestionOrderId());
            tbTransactionOrderMapper.updateTbTransactionOrderForQuestionOrderId(tbTransactionOrder);
        });
        return i;
    }

    @Override
    public TbTransactionQuestionOrder selectTbTransactionQuestionOrderById(Long id) {
        return tbTransactionQuestionOrderMapper.selectTbTransactionQuestionOrderById(id);
    }

    @Override
    public int updateTbTransactionQuestionOrder(TbTransactionQuestionOrder tbTransactionQuestionOrder) {
        if(tbTransactionQuestionOrder.getQuestionOrderStatus()!=null && tbTransactionQuestionOrder.getQuestionOrderStatus()!=1){
            tbTransactionQuestionOrder.setQuestionOrderRole(null);
        }
        return tbTransactionQuestionOrderMapper.updateTbTransactionQuestionOrder(tbTransactionQuestionOrder);
    }
}
