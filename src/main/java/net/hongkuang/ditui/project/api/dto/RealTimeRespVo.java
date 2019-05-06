package net.hongkuang.ditui.project.api.dto;

public class RealTimeRespVo {
    private String taskDate;
    private String startTimeNode;
    private String stopTimeNode;
    private Boolean grabFlag;

    public Boolean getGrabFlag() {
        return grabFlag;
    }

    public void setGrabFlag(Boolean grabFlag) {
        this.grabFlag = grabFlag;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getStartTimeNode() {
        return startTimeNode;
    }

    public void setStartTimeNode(String startTimeNode) {
        this.startTimeNode = startTimeNode;
    }

    public String getStopTimeNode() {
        return stopTimeNode;
    }

    public void setStopTimeNode(String stopTimeNode) {
        this.stopTimeNode = stopTimeNode;
    }
}
