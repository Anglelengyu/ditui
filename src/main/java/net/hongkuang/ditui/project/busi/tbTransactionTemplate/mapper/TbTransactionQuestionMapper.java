package net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper;

import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import org.springframework.stereotype.Repository;

/**
 * 淘宝模版问题 数据层
 *
 * @author:zy
 * @date: 2019/4/3
 */
@Repository
public interface TbTransactionQuestionMapper {

    /**
     * 新增淘宝模版问题
     *
     * @param tbTransactionQuestion
     * @return 淘宝模版问题信息
     */
    int saveTbTransactionQuestion(TbTransactionQuestion tbTransactionQuestion);

    /**
     * 更新淘宝模版问题
     *
     * @param tbTransactionQuestion
     * @return 淘宝模版问题信息
     */
    int updateTbTransactionQuestion(TbTransactionQuestion tbTransactionQuestion);

    /**
     * 删除模版问题
     *
     * @param id
     * @return 淘宝模版信息
     */
    int deleteTbTransactionQuestionById(Long id);

    /**
     * 查询淘宝模版问题
     *
     * @param id 淘宝模版问题id
     * @return 淘宝模版
     */
    TbTransactionQuestion selectTbTransactionQuestionById(Long id);

    /**
     * 删除淘宝问题
     *
     * @param id
     * @return 淘宝关键词信息
     */
    int deleteTbTransactionQuestionByTemplateIds(String[] id);

    /**
     * 删除订单问题
     *
     * @param id
     * @return 淘宝关键词信息
     */
    int deleteTbTransactionQuestionByOrderIds(String[] id);

}
