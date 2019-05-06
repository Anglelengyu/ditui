package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/12.
 */
public class TodoTaskRespVo {
    private String taskId;

    public TodoTaskRespVo() {

    }

    public TodoTaskRespVo(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "TodoTaskRespVo{" +
                "taskId='" + taskId + '\'' +
                '}';
    }
}
