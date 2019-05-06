package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.mapper.TbTransactionQuestionOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public int saveTbTransactionQuestionOrderList(String[] orderIds,String questionOrderRemark) {



        return 0;
    }
}
