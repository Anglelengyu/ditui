package net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 实时任务时间设置表 busi_real_time_ground_setting
 *
 * @author:zy
 * @date: 2019/4/18
 */
public class RealTimeGroundSetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**  */
    private String realTimeId;
    /**
     * 实时任务数量
     */
    private Integer taskNum;
    /**  */
    private String startTimeNode;
    /**  */
    private String stopTimeNode;
    /**
     * 状态1正常0禁用
     */
    private Integer status;
    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 是否锁定接单时间（0-否 1-是）
     */
    private Integer orLock;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;
    private String taskDate;

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

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRealTimeId(String realTimeId) {
        this.realTimeId = realTimeId;
    }

    public String getRealTimeId() {
        return realTimeId;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setStartTimeNode(String startTimeNode) {
        this.startTimeNode = startTimeNode;
    }

    public String getStartTimeNode() {
        return startTimeNode;
    }

    public void setStopTimeNode(String stopTimeNode) {
        this.stopTimeNode = stopTimeNode;
    }

    public String getStopTimeNode() {
        return stopTimeNode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("realTimeId", getRealTimeId())
                .append("taskNum", getTaskNum())
                .append("startTimeNode", getStartTimeNode())
                .append("stopTimeNode", getStopTimeNode())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
