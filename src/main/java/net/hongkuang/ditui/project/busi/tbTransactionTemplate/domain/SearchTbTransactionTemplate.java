package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

/**
 * @author:zy
 * @date: 2019/4/4
 */
public class SearchTbTransactionTemplate extends TbTransactionTemplate {
    private String name;
    private String startTime;
    private String endTime;

    /**
     * 模版ID
     */
    private Long id;

    /**
     * 掌柜昵称
     */
    private String managerUserName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品简称
     */
    private String goodsName;

    /**
     * 团队是否已备注 0-否 1-是
     */
    private Integer orTeamRemark;

    /**
     * 掌柜是否已备注 0-否 1-是
     */
    private Integer orManagerRemark;


    public Integer getOrManagerRemark() {
        return orManagerRemark;
    }

    public void setOrManagerRemark(Integer orManagerRemark) {
        this.orManagerRemark = orManagerRemark;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getManagerUserName() {
        return managerUserName;
    }

    @Override
    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    @Override
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String getGoodsName() {
        return goodsName;
    }

    @Override
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getOrTeamRemark() {
        return orTeamRemark;
    }

    public void setOrTeamRemark(Integer orTeamRemark) {
        this.orTeamRemark = orTeamRemark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
