package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/12.
 */
public class TaskCancelReqVo {
    private Long userId;
    private String taskId;

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

    @Override
    public String toString() {
        return "TaskCancelReqVo{" +
                "userId=" + userId +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
