package net.hongkuang.ditui.project.api.dto;

import net.hongkuang.ditui.project.busi.order.domain.Order;

import java.util.List;

/**
 * Created by apple on 2019/1/12.
 */
public class TaskDetailRespVo {

    private String taskId;
    private Integer orderNum;
    private List<Order> orderList;
    private Integer taskStatus;

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "TaskDetailRespVo{" +
                "taskId='" + taskId + '\'' +
                ", orderNum=" + orderNum +
                ", orderList=" + orderList +
                '}';
    }
}
