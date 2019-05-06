package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain;



/**
 * @author:zy
 * @date: 2019/4/26
 */
public class SearchTbTransactionQuestionOrder extends TbTransactionQuestionOrder {
    private String startTime;
    private String endTime;

    /**
     * 团队是否已备注 0-否 1-是
     */
    private Integer orTeamRemark;

    /**
     * 掌柜是否已备注 0-否 1-是
     */
    private Integer orManagerRemark;


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
