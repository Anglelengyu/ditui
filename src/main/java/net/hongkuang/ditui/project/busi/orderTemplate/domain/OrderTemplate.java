package net.hongkuang.ditui.project.busi.orderTemplate.domain;

import com.google.common.collect.Lists;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;

import java.util.List;


/**
 * 订单模版表 busi_order_template
 *
 * @author:zy
 * @date: 2019/2/25
 */
public class OrderTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String orderId;
    /**
     * 类目
     */
    @Excel(name = "类目名称")
    private String category;
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String shopName;
    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String goodsName;
    /**
     * 备注
     */
    @Excel(name = "下单选项")
    private String remarks;
    /**
     * 订单图片
     */
    private String orderImg;
    /**
     * 商品本金
     */
    private Long unitPrice;
    @Excel(name = "商品本金")
    private String unitPriceYuan;
    /**
     * 商品佣金
     */
    private Long commission;
    @Excel(name = "商品佣金")
    private String commissionYuan;
    /**
     * 状态1-待提交2-待审核3-审核通过4-审核未通过
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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
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

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Long getCommission() {
        return commission;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUnitPriceYuan() {
        return unitPriceYuan;
    }

    public void setUnitPriceYuan(String unitPriceYuan) {
        this.unitPriceYuan = unitPriceYuan;
    }

    public String getCommissionYuan() {
        return commissionYuan;
    }

    public void setCommissionYuan(String commissionYuan) {
        this.commissionYuan = commissionYuan;
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
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", category='" + category + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                ", orderImg='" + orderImg + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitPriceYuan='" + unitPriceYuan + '\'' +
                ", commission=" + commission +
                ", commissionYuan='" + commissionYuan + '\'' +
                '}';
    }
}
