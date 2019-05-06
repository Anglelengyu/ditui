package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

/**
 * @author:zy
 * @date: 2019/4/9
 */
public class Goods {

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 商品主图
     */
    private String goodsImg;

    /**
     * 商品简称
     */
    private String goodsName;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
