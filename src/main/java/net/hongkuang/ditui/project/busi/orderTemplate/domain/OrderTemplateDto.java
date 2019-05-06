package net.hongkuang.ditui.project.busi.orderTemplate.domain;

import com.google.common.collect.Lists;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author:zy
 * @date: 2019/2/26
 */
public class OrderTemplateDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 类目
     */
    private String category;
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 订单图片
     */
    private String orderImg;
    /**
     * 商品本金
     */
    private BigDecimal unitPrice;
    private String unitPriceYuan;
    /**
     * 商品佣金
     */
    private BigDecimal commission;
    private String commissionYuan;
    /**
     * 状态5-待提交 6-待审核 7-审核通过 8-审核未通过
     */
    private Integer status;
    /**
     * 商品平台 1-淘宝 2-京东 3-天猫 4-拼多多
     */
    private Integer shopPlatform;

    private List<OrderTemplateKeyWords> orderTemplateKeyWords = Lists.newArrayList();


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceYuan() {
        return unitPriceYuan;
    }

    public void setUnitPriceYuan(String unitPriceYuan) {
        this.unitPriceYuan = unitPriceYuan;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getCommissionYuan() {
        return commissionYuan;
    }

    public void setCommissionYuan(String commissionYuan) {
        this.commissionYuan = commissionYuan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderTemplateKeyWords> getOrderTemplateKeyWords() {
        return orderTemplateKeyWords;
    }

    public void setOrderTemplateKeyWords(List<OrderTemplateKeyWords> orderTemplateKeyWords) {
        this.orderTemplateKeyWords = orderTemplateKeyWords;
    }

    public Integer getShopPlatform() {
        return shopPlatform;
    }

    public void setShopPlatform(Integer shopPlatform) {
        this.shopPlatform = shopPlatform;
    }

    @Override
    public String toString() {
        return "OrderTemplateDto{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderImg='" + orderImg + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitPriceYuan=" + unitPriceYuan +
                ", commission=" + commission +
                ", commissionYuan=" + commissionYuan +
                ", status=" + status +
                ", shopPlatform=" + shopPlatform +
                ", orderTemplateKeyWords=" + orderTemplateKeyWords +
                '}';
    }
}
