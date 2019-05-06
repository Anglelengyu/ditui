package net.hongkuang.ditui.project.api.dto;

import net.hongkuang.ditui.project.busi.task.domain.Task;

public class TaskSubmitReqVo extends Task {

    private String orderInfo;
    private String taskInfo;
    private String checkOrderResult;

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getCheckOrderResult() {
        return checkOrderResult;
    }

    public void setCheckOrderResult(String checkOrderResult) {
        this.checkOrderResult = checkOrderResult;
    }
}
