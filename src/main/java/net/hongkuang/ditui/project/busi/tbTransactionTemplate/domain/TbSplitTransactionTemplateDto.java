package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 淘宝成交模版
 *
 * @author:zy
 * @date: 2019/4/3
 */
public class TbSplitTransactionTemplateDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /* 模版id */
    private Long id;

    /* 放单方式 （1-线上立返 2-线上货返 3-地推立返）*/
    private Integer executeMethod;

    private Integer totalNumberTotal;

    private Integer splitTotalNumberTotal;

    /* 关键词列表 */
    private List<TbTransactionKeyWordsDto> tbTransactionKeyWords;

    public Integer getTotalNumberTotal() {
        return totalNumberTotal;
    }

    public void setTotalNumberTotal(Integer totalNumberTotal) {
        this.totalNumberTotal = totalNumberTotal;
    }

    public Integer getSplitTotalNumberTotal() {
        return splitTotalNumberTotal;
    }

    public void setSplitTotalNumberTotal(Integer splitTotalNumberTotal) {
        this.splitTotalNumberTotal = splitTotalNumberTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(Integer executeMethod) {
        this.executeMethod = executeMethod;
    }

    public List<TbTransactionKeyWordsDto> getTbTransactionKeyWords() {
        return tbTransactionKeyWords;
    }

    public void setTbTransactionKeyWords(List<TbTransactionKeyWordsDto> tbTransactionKeyWords) {
        this.tbTransactionKeyWords = tbTransactionKeyWords;
    }
}
