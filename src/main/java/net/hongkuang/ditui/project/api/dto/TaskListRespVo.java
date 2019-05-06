package net.hongkuang.ditui.project.api.dto;

import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.project.busi.task.domain.Task;

import java.util.List;

/**
 * Created by apple on 2019/1/12.
 */
public class TaskListRespVo {

    private String date;
    private List<SimpleTaskVo> taskList;

    public static class SimpleTaskVo {
        private String taskId;
        private Integer orderNum;
        private Long taskCommission;
        private Long taskCorpus;
        private Integer taskStatus;

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

        public Long getTaskCommission() {
            return taskCommission;
        }

        public void setTaskCommission(Long taskCommission) {
            this.taskCommission = taskCommission;
        }

        public Long getTaskCorpus() {
            return taskCorpus;
        }

        public void setTaskCorpus(Long taskCorpus) {
            this.taskCorpus = taskCorpus;
        }

        public Integer getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(Integer taskStatus) {
            this.taskStatus = taskStatus;
        }

        public static SimpleTaskVo buildTask(Task task) {
            SimpleTaskVo simpleTaskVo = new SimpleTaskVo();
            BeanUtils.copyBeanProp(simpleTaskVo, task);
            return simpleTaskVo;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SimpleTaskVo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<SimpleTaskVo> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "TaskListRespVo{" +
                "date='" + date + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}
