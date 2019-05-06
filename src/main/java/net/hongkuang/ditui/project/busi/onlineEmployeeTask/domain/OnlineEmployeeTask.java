package net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain;

import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 线下员工任务表 busi_online_employee_task
 *
 * @author:zy
 * @date: 2019/4/19
 */
public class OnlineEmployeeTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**  */
    private String onlineEmployeeTaskId;
    /**  */
    private String employeeId;
    /**
     * 团队ID
     */
    private Long teamId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 状态1已完成2未完成3取消中4已取消
     */
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getOnlineEmployeeTaskId() {
        return onlineEmployeeTaskId;
    }

    public void setOnlineEmployeeTaskId(String onlineEmployeeTaskId) {
        this.onlineEmployeeTaskId = onlineEmployeeTaskId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt2() {
        return ext2;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("onlineEmployeeTaskId", getOnlineEmployeeTaskId())
                .append("employeeId", getEmployeeId())
                .append("taskId", getTaskId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .toString();
    }

    public static OnlineEmployeeTask build(String taskId, String userId) {
        OnlineEmployeeTask onlineEmployeeTask = new OnlineEmployeeTask();
        onlineEmployeeTask.setOnlineEmployeeTaskId(RandomUtil.genString());
        onlineEmployeeTask.setEmployeeId(userId);
        onlineEmployeeTask.setTaskId(taskId);
        onlineEmployeeTask.setStatus(1);
        return onlineEmployeeTask;
    }
}
