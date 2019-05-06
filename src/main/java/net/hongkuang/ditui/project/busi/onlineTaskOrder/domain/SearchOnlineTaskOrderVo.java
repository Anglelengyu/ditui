package net.hongkuang.ditui.project.busi.onlineTaskOrder.domain;


import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/24
 */
public class SearchOnlineTaskOrderVo extends TbTransactionOrder {
    private String name;
    private String startTime;
    private String endTime;

    //提交时间
    private String referStartTime;
    private String referEndTime;


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

    public String getReferStartTime() {
        return referStartTime;
    }

    public void setReferStartTime(String referStartTime) {
        this.referStartTime = referStartTime;
    }

    public String getReferEndTime() {
        return referEndTime;
    }

    public void setReferEndTime(String referEndTime) {
        this.referEndTime = referEndTime;
    }

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
