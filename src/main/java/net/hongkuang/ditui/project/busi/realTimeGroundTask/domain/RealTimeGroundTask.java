package net.hongkuang.ditui.project.busi.realTimeGroundTask.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeTask.enums.RealTimeTaskStatus;
import net.hongkuang.ditui.project.busi.task.domain.Task;

import java.util.Date;


/**
 * 实时任务表 busi_real_time_ground_task
 *
 * @author:zy
 * @date: 2019/4/18
 */
public class RealTimeGroundTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 逻辑id
     */
    private String realTimeTaskId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String stopTime;
    /**
     * 团队ID
     */
    private Long teamId;
    /**
     * 状态1未被抢 2已被抢
     */
    private Integer status;
    /**  */
    private Date createTime;
    /**  */
    private Date updateTime;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;
    /**  */
    private String taskDate;

    /**
     * 是否锁定接单时间（0-否 1-是）
     */
    private Integer orLock;

    public RealTimeGroundTask() {

    }

    public RealTimeGroundTask(RealTimeGroundSetting realTimeGroundSetting, GroundTask groundTask) {
        this.startTime = realTimeGroundSetting.getStartTimeNode();
        this.stopTime = realTimeGroundSetting.getStopTimeNode();
        this.taskDate = realTimeGroundSetting.getTaskDate();
        this.orLock = realTimeGroundSetting.getOrLock();
        this.status = RealTimeTaskStatus.DISRUSH.getCode();
        this.taskId = groundTask.getTaskId();
    }

    public Integer getOrLock() {
        return orLock;
    }

    public void setOrLock(Integer orLock) {
        this.orLock = orLock;
    }

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

    public void setRealTimeTaskId(String realTimeTaskId) {
        this.realTimeTaskId = realTimeTaskId;
    }

    public String getRealTimeTaskId() {
        return realTimeTaskId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "RealTimeTask{" +
                "id=" + id +
                ", realTimeTaskId='" + realTimeTaskId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", taskDate='" + taskDate + '\'' +
                '}';
    }
}
