package net.hongkuang.ditui.project.busi.shop.domain;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 店铺表 busi_shop
 *
 * @author yj
 * @date 2018-12-30
 */
public class Shop extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    @Excel(name = "店铺编号")
    private Long id;
    /**
     * 逻辑id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String name;
    /**
     * 掌柜姓名
     */
    @Excel(name = "掌柜姓名")
    private String managerName;
    @Excel(name = "旺旺名称")
    private String wangwang;
    /**
     * 掌柜姓名
     */
    @Excel(name = "介绍人")
    private String referUserName;
    /**
     * 介绍人
     */
    private String referUserId;
    /**
     * 掌柜id
     */
    private String managerId;


    /**
     * 类目
     */
    @Excel(name = "店铺类目")
    private String category;
    /**
     * 状态1正常0禁用
     */
    @Excel(name = "店铺状态", readConverterExp = "0=正常,1=停用")
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;

    public String getReferUserName() {
        return referUserName;
    }

    public void setReferUserName(String referUserName) {
        this.referUserName = referUserName;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getReferUserId() {
        return referUserId;
    }

    public void setReferUserId(String referUserId) {
        this.referUserId = referUserId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt2() {
        return ext2;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shopId", getShopId())
                .append("referUserId", getReferUserId())
                .append("managerId", getManagerId())
                .append("managerName", getManagerName())
                .append("name", getName())
                .append("category", getCategory())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
