package net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper;

import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.SearchTbTransactionTemplate;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 淘宝模版 数据层
 *
 * @author:zy
 * @date: 2019/4/3
 */
@Repository
public interface TbTransactionTemplateMapper {

    /**
     * 查询淘宝模版列表
     *
     * @param searchTbTransactionTemplate 淘宝模版
     * @return 淘宝模版集合
     */
    List<TbTransactionTemplate> selectTbTransactionTemplateList(SearchTbTransactionTemplate searchTbTransactionTemplate);

    /**
     * 查询淘宝模版
     *
     * @param id 淘宝模版
     * @return 淘宝模版
     */
    TbTransactionTemplate selectTbTransactionTemplateById(Long id);

    /**
     * 新增淘宝模版
     *
     * @param tbTransactionTemplate
     * @return 淘宝模版信息
     */
    int saveTbTransactionTemplate(TbTransactionTemplate tbTransactionTemplate);

    /**
     * 更新淘宝模版
     *
     * @param tbTransactionTemplate
     * @return 淘宝模版信息
     */
    int updateTbTransactionTemplate(TbTransactionTemplate tbTransactionTemplate);

    /**
     * 删除淘宝模版
     *
     * @param ids
     * @return 淘宝模版信息
     */
    int deleteTbTransactionTemplateByIds(String[] ids);

    /**
     * 修改订单
     *
     * @param status 订单信息
     * @return 结果
     */
    int updateTbTransactionTemplateStatus(@Param("ids") String[] ids, @Param("status") Integer status);

    /**
     * 校验模版名称是否唯一
     *
     * @param templateName 模版名称
     * @return 结果
     */
    int checkTemplateNameUnique(@Param("templateName") String templateName, @Param("managerId") Long managerId);

    /**
     * 根据模版id根据关键词数量
     *
     * @param ids 模版ids
     * @return 结果
     */
    int getTbTransactionTemplateKeyWordsTotal(String[] ids);

    /**
     * 根据模版id根据关键词数量
     *
     * @param ids 模版ids
     * @return 结果
     */
    int getTbTransactionTemplateNoGroupClassifys(String[] ids);

    /**
     * 分配给合作团队
     *
     * @param id
     * @return 结果
     */
    int distributionTeam(@Param("id") Long id, @Param("receiptTeamId") Long receiptTeamId, @Param("receiptTeamName") String receiptTeamName);

    /**
     * 更新淘宝模版备注
     *
     * @param tbTransactionTemplate
     * @return 淘宝模版信息
     */
    int updateTbTransactionTemplateRemark(TbTransactionTemplate tbTransactionTemplate);

}
