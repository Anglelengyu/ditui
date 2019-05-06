package net.hongkuang.ditui.project.busi.order.dto;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;

import java.math.BigDecimal;

/**
 * Created by apple on 2019/1/1.
 */
public class OrderDto {

    @Excel(name = "类目名称")
    private String category;                // 类目名称
    private String shopId;                  // 店铺ID
    @Excel(name = "店铺名称")
    private String shopName;                // 店铺名称
    @Excel(name = "商品名称")
    private String goodsName;               // 商品名称
    @Excel(name = "关键字")
    private String keyWord;                 // 关键字
    @Excel(name = "下单选项")
    private String remarks;                 // 下单选项
    @Excel(name = "订单数量")
    private Integer orderCount;             // 订单数量
    @Excel(name = "商品本金")
    private BigDecimal unitPrice;              // 商品本金
    @Excel(name = "图片")
    private String orderImg;       // 商品图片
    @Excel(name = "商品佣金")
    private BigDecimal commission;             // 商品佣金

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "category='" + category + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderCount=" + orderCount +
                ", unitPrice=" + unitPrice +
                ", orderImg='" + orderImg + '\'' +
                ", commission=" + commission +
                '}';
    }
}
