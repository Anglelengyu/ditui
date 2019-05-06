package net.hongkuang.ditui.project.busi.tbTransactionTemplate.service;


import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.SearchTbTransactionTemplate;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbSplitTransactionTemplateDto;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionTemplate;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionTemplateDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 淘宝模版
 *
 * @author:zy
 * @date: 2019/4/3
 */
public interface ITbTransactionTemplateService {

    /**
     * 查询淘宝模版列表
     *
     * @param searchTbTransactionTemplate 模版信息
     * @return 淘宝模版集合
     */
    public List<TbTransactionTemplate> selectTbTransactionTemplateList(SearchTbTransactionTemplate searchTbTransactionTemplate);

    /**
     * 查询淘宝模版
     *
     * @param id 模版信息
     * @return 淘宝模版集合
     */
    public TbTransactionTemplate selectTbTransactionTemplateById(Long id);

    /**
     * 保存淘宝模版
     *
     * @param tbTransactionTemplateDto 信息
     * @return 结果
     */
    int insertTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto);

    /**
     * 保存淘宝模版
     *
     * @param tbTransactionTemplate 信息
     * @return 结果
     */
    int updateTbTransactionTemplate(TbTransactionTemplate tbTransactionTemplate);

    /**
     * 提交保存模版
     *
     * @param tbTransactionTemplateDto 关键字信息
     * @return 结果
     */
    public int referTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto);

    /**
     * 提交保存模版
     *
     * @param tbTransactionTemplateDto 关键字信息
     * @return 结果
     */
    public int againReferTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto);

    /**
     * 保存淘宝模版
     *
     * @param tbTransactionTemplateDto 信息
     * @return 结果
     */
    int updateWaitcheckTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto);

    /**
     * 审核订单
     *
     * @param status 订单信息
     * @return 结果
     */
    public int executeCheck(@Param("ids") String ids, @Param("status") Integer status);

    /**
     * 删除模版信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbTransactionTemplateByIds(String ids);

    /**
     * 校验模版名称是否唯一
     *
     * @param templateName 模版名称
     * @return 结果
     */
    String checkTemplateNameUnique(String templateName, Long managerId);

    /**
     * 拆分模版
     *
     * @param tbSplitTransactionTemplateDto 信息
     * @return 结果
     */
    int splitTemplate(TbSplitTransactionTemplateDto tbSplitTransactionTemplateDto);

    /**
     * 获取关键词总量
     *
     * @param ids 信息
     * @return 结果
     */
    public AjaxResult getTbTransactionTemplateKeyWordsTotal(String ids);

    /**
     * 拆分订单
     *
     * @param ids 信息
     * @return 结果
     */
    int splitOrder(String ids);

    /**
     * 分配合作团队
     *
     * @param id 信息
     * @return 结果
     */
    int distributionTeam(Long id, Long teamId, String teamName);

    /**
     * 保存模版备注
     *
     * @param tbTransactionTemplateDto 信息
     * @return 结果
     */
    int updateTbTransactionTemplateRemark(TbTransactionTemplateDto tbTransactionTemplateDto);

}
