package net.hongkuang.ditui.project.busi.onlineTask.domain;


public class SearchOnlineTask extends OnlineTask {

    private String taskId;
    private String goodsName;
    private String startTime;
    private String endTime;
    private Integer taskStatus;
    private Integer orderNum;
    private String buyerNick;
    private String userName;
    private Long teamId;

    /**
     * 团队是否已备注 0-否 1-是
     */
    private Integer orTeamRemark;

    public Integer getOrTeamRemark() {
        return orTeamRemark;
    }

    public void setOrTeamRemark(Integer orTeamRemark) {
        this.orTeamRemark = orTeamRemark;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public Integer getOrderNum() {
        return orderNum;
    }

    @Override
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SearchTask{" +
                "taskId='" + taskId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", taskStatus=" + taskStatus +
                ", orderNum=" + orderNum +
                ", buyerNick='" + buyerNick + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
