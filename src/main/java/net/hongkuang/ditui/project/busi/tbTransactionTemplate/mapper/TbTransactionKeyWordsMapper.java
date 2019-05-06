package net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper;

import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.SearchTbTransactionKeyWords;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionKeyWords;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 淘宝关键词 数据层
 *
 * @author:zy
 * @date: 2019/4/3
 */
@Repository
public interface TbTransactionKeyWordsMapper {

    /**
     * 查询淘宝关键词列表
     *
     * @param searchTbTransactionKeyWords 淘宝关键词列表
     * @return 淘宝关键词集合
     */
    List<TbTransactionKeyWords> selectTbTransactionKeyWordsList(SearchTbTransactionKeyWords searchTbTransactionKeyWords);

    /**
     * 新增淘宝关键词
     *
     * @param tbTransactionKeyWords
     * @return 淘宝关键词信息
     */
    int saveTbTransactionKeyWords(TbTransactionKeyWords tbTransactionKeyWords);

    /**
     * 删除淘宝关键词
     *
     * @param id
     * @return 淘宝关键词信息
     */
    int deleteTbTransactionKeyWordsByTemplateId(Long id);

    /**
     * 根据模版ID删除淘宝关键词
     *
     * @param id
     * @return 淘宝关键词信息
     */
    int deleteTbTransactionKeyWordsByTemplateIds(String[] id);

}
