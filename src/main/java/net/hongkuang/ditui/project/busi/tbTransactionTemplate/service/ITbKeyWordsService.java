package net.hongkuang.ditui.project.busi.tbTransactionTemplate.service;


import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionKeyWords;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionTemplate;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionTemplateDto;

import java.util.List;

/**
 * 淘宝关键词
 *
 * @author:zy
 * @date: 2019/4/3
 */
public interface ITbKeyWordsService {

    /**
     * 查询关键字信息
     *
     * @param templateId 模版ID
     * @return 关键词信息
     */
    public List<TbTransactionKeyWords> selectTbTransactionKeyWordsList(Long templateId);

    /**
     * 修改关键字
     *
     * @param tbTransactionTemplateDto 关键字信息
     * @return 结果
     */
    public int updateTbTransactionKeyWords(TbTransactionTemplateDto tbTransactionTemplateDto);

}
