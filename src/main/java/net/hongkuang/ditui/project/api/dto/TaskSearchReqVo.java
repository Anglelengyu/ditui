package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/12.
 */
public class TaskSearchReqVo {
    private Long userId;
    private String taskId;
    private String goodsName;
    private String startTime;
    private String endTime;
    private Integer taskStatus;
    private Integer orderNum;
    private String buyerNick;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Override
    public String toString() {
        return "TaskSearchReqVo{" +
                "userId=" + userId +
                ", taskId='" + taskId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", taskStatus=" + taskStatus +
                ", orderNum=" + orderNum +
                ", buyerNick='" + buyerNick + '\'' +
                '}';
    }
}
