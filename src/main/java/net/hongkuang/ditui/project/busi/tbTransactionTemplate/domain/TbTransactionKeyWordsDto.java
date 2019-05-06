package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;


/**
 * 淘宝关键词
 *
 * @author:zy
 * @date: 2019/4/3
 */
public class TbTransactionKeyWordsDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关键词id
     */
    private Long id;

    /**
     * 模版ID
     */
    private Long templateId;

    /**
     * 关键词名称
     */
    private String name;

    /**
     * 单量
     */
    private Integer totalNumber = 0;

    /**
     * 手机端
     */
    private Integer appNumber = 0;

    /**
     * 电脑端
     */
    private Integer pcNumber = 0;

    /**
     * 收藏
     */
    private Integer collectionNumber = 0;

    /**
     * 加购
     */
    private Integer cartNumber = 0;

    /**
     * 收藏+加购
     */
    private Integer collectionCartNumber = 0;


    /**
     * 单量
     */
    private Integer splitTotalNumber = 0;

    /**
     * 手机端
     */
    private Integer splitAppNumber = 0;

    /**
     * 电脑端
     */
    private Integer splitPcNumber = 0;

    /**
     * 收藏
     */
    private Integer splitCollectionNumber = 0;

    /**
     * 加购
     */
    private Integer splitCartNumber = 0;

    /**
     * 收藏+加购
     */
    private Integer splitCollectionCartNumber = 0;


    public Integer getSplitTotalNumber() {
        return splitTotalNumber;
    }

    public void setSplitTotalNumber(Integer splitTotalNumber) {
        this.splitTotalNumber = splitTotalNumber;
    }

    public Integer getSplitAppNumber() {
        return splitAppNumber;
    }

    public void setSplitAppNumber(Integer splitAppNumber) {
        this.splitAppNumber = splitAppNumber;
    }

    public Integer getSplitPcNumber() {
        return splitPcNumber;
    }

    public void setSplitPcNumber(Integer splitPcNumber) {
        this.splitPcNumber = splitPcNumber;
    }

    public Integer getSplitCollectionNumber() {
        return splitCollectionNumber;
    }

    public void setSplitCollectionNumber(Integer splitCollectionNumber) {
        this.splitCollectionNumber = splitCollectionNumber;
    }

    public Integer getSplitCartNumber() {
        return splitCartNumber;
    }

    public void setSplitCartNumber(Integer splitCartNumber) {
        this.splitCartNumber = splitCartNumber;
    }

    public Integer getSplitCollectionCartNumber() {
        return splitCollectionCartNumber;
    }

    public void setSplitCollectionCartNumber(Integer splitCollectionCartNumber) {
        this.splitCollectionCartNumber = splitCollectionCartNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(Integer appNumber) {
        this.appNumber = appNumber;
    }

    public Integer getPcNumber() {
        return pcNumber;
    }

    public void setPcNumber(Integer pcNumber) {
        this.pcNumber = pcNumber;
    }

    public Integer getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(Integer collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public Integer getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(Integer cartNumber) {
        this.cartNumber = cartNumber;
    }

    public Integer getCollectionCartNumber() {
        return collectionCartNumber;
    }

    public void setCollectionCartNumber(Integer collectionCartNumber) {
        this.collectionCartNumber = collectionCartNumber;
    }
}
