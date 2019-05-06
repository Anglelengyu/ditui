package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/12.
 */
public class TodoTaskReqVo {
    private String startTime;
    private String stopTime;
    private Long userId;
    private Integer taskCount = 1;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }


    @Override
    public String toString() {
        return "TodoTaskReqVo{" +
                "startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", userId='" + userId + '\'' +
                ", taskCount=" + taskCount +
                '}';
    }
}
