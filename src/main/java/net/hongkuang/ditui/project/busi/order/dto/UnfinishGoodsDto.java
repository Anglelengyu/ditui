package net.hongkuang.ditui.project.busi.order.dto;

/**
 * Created by apple on 2019/1/3.
 */
public class UnfinishGoodsDto {
    // 商品名称
    private String goodsName;
    private Integer goodsCount;
    private String category;
    private Integer categoryCount;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    @Override
    public String toString() {
        return "UnfinishGoodsDto{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", category='" + category + '\'' +
                ", categoryCount=" + categoryCount +
                '}';
    }
}
