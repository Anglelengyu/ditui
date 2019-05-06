package net.hongkuang.ditui.project.busi.orderTemplate.service;

import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateKeyWords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模版关键字 服务层
 *
 * @author:zy
 * @date: 2019/2/26
 */
public interface IOrderTemplateKeyWordsService {
    /**
     * 查询关键字信息
     *
     * @param id 模版ID
     * @return 关键词信息
     */
    public List<OrderTemplateKeyWords> selectOrderTemplateKeyWordsList(Long id);

    public int getOrderTemplateKeyWordsCount(Long orderTemplateId);

}
