package net.hongkuang.ditui.project.busi.task.domain;

public class SearchTask extends Task {

    private String taskId;
    private String goodsName;
    private String startTime;
    private String endTime;
    private Integer taskStatus;
    private Integer orderNum;
    private String buyerNick;
    private String saleName;

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

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
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
                ", saleName='" + saleName + '\'' +
                '}';
    }
}
