package net.hongkuang.ditui.project.busi.order.domain;


import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/15
 */
public class SearchTbTransactionOrder extends TbTransactionOrder {
    private String name;
    private String startTime;
    private String endTime;

    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 团队昵称
     */
    private String teamName;

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

    private List<String> sortGoodsNameList;

    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public List<String> getSortGoodsNameList() {
        return sortGoodsNameList;
    }

    public void setSortGoodsNameList(List<String> sortGoodsNameList) {
        this.sortGoodsNameList = sortGoodsNameList;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    @Override
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

    public Integer getOrManagerRemark() {
        return orManagerRemark;
    }

    public void setOrManagerRemark(Integer orManagerRemark) {
        this.orManagerRemark = orManagerRemark;
    }

    @Override
    public Long getTeamId() {
        return teamId;
    }

    @Override
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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
