package net.hongkuang.ditui.project.busi.order.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;


/**
 * 淘宝买家要求
 *
 * @author:zy
 * @date: 2019/4/12
 */
public class TbTransactionBuyerRequire extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 要求id
     */
    private Long id;

    /**
     * 买家性别（1-男 2-女）
     */
    private Integer sex;

    /**
     * 买家年龄
     */
    private Integer age;

    /**
     * 信用等级
     */
    private String creditRating;

    /**
     * 商品类目
     */
    private String category;

    /**
     * 月销额度
     */
    private String monthlySalesQuota;

    /**
     * 淘气值
     */
    private String naughtyValue;

    /**
     * 买家地域
     */
    private String region;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMonthlySalesQuota() {
        return monthlySalesQuota;
    }

    public void setMonthlySalesQuota(String monthlySalesQuota) {
        this.monthlySalesQuota = monthlySalesQuota;
    }

    public String getNaughtyValue() {
        return naughtyValue;
    }

    public void setNaughtyValue(String naughtyValue) {
        this.naughtyValue = naughtyValue;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
