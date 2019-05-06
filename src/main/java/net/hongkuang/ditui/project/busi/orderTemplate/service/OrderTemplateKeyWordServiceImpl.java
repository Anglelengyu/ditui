package net.hongkuang.ditui.project.busi.orderTemplate.service;

import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateKeyWords;
import net.hongkuang.ditui.project.busi.orderTemplate.mapper.OrderTemplateKeyWordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单模版关键词 服务层实现
 *
 * @author:zy
 * @date: 2019/2/27
 */
@Service
public class OrderTemplateKeyWordServiceImpl implements IOrderTemplateKeyWordsService {
    @Autowired
    private OrderTemplateKeyWordsMapper orderTemplateKeyWordsMapper;

    /**
     * 查询模版关键词列表
     *
     * @param id 模版ID
     * @return 关键词集合
     */
    @Override
    public List<OrderTemplateKeyWords> selectOrderTemplateKeyWordsList(Long id) {
        return orderTemplateKeyWordsMapper.selectOrderTemplateKeyWordsList(id);
    }

    /**
     * 查询模版关键词数量
     *
     * @param id 模版ID
     * @return 关键词集合
     */
    @Override
    public int getOrderTemplateKeyWordsCount(Long id) {
        return orderTemplateKeyWordsMapper.getOrderTemplateKeyWordsCount(id);
    }

}
