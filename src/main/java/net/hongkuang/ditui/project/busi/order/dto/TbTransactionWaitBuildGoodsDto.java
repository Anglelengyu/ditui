package net.hongkuang.ditui.project.busi.order.dto;

/**
 * @author:zy
 * @date: 2019/4/12
 */
public class TbTransactionWaitBuildGoodsDto {
    // 商品名称
    private String goodsName;
    private Integer goodsCount;
    //组单分类
    private String groupClassifys;
    //组单分类总量
    private Integer groupClassifysCount;

    public String getGroupClassifys() {
        return groupClassifys;
    }

    public void setGroupClassifys(String groupClassifys) {
        this.groupClassifys = groupClassifys;
    }

    public Integer getGroupClassifysCount() {
        return groupClassifysCount;
    }

    public void setGroupClassifysCount(Integer groupClassifysCount) {
        this.groupClassifysCount = groupClassifysCount;
    }

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

    @Override
    public String toString() {
        return "UnfinishGoodsDto{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                '}';
    }
}
