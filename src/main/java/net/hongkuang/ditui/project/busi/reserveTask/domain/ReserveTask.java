package net.hongkuang.ditui.project.busi.reserveTask.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 储备任务表 busi_reserve_task
 *
 * @author yj
 * @date 2019-01-03
 */
public class ReserveTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;
    /**
     * 逻辑id
     */
    private String reserveId;
    /**  */
    private String taskId;
    /**
     * 状态1未完成2已完成3取消中4已取消
     */
    private Integer status;
    /**
     * 储备任务日期注：2018-12-28
     */
    private String reserveDate;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveId() {
        return reserveId;
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

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveDate() {
        return reserveDate;
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
                .append("reserveId", getReserveId())
                .append("taskId", getTaskId())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("reserveDate", getReserveDate())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
