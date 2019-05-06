package net.hongkuang.ditui.project.busi.taskCancelApproval.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 任务取消审批表 busi_task_cancel_approval
 *
 * @author yj
 * @date 2019-01-06
 */
public class TaskCancelApproval extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;
    /**
     * 逻辑id
     */
    private String approvalId;
    /**
     * 任务编号
     */
    private String taskId;
    /**
     * 取消原因
     */
    private String remark;
    /**
     * 业务员id
     */
    private String saleId;
    /**
     * 业务员手机号
     */
    private String salePhone;
    /**
     * 业务员姓名
     */
    private String saleName;
    /**
     * 业务员所在地区
     */
    private String saleArea;
    /**
     * 审批状态1未审批2通过3驳回
     */
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSalePhone(String salePhone) {
        this.salePhone = salePhone;
    }

    public String getSalePhone() {
        return salePhone;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("approvalId", getApprovalId())
                .append("taskId", getTaskId())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("saleId", getSaleId())
                .append("salePhone", getSalePhone())
                .append("saleName", getSaleName())
                .append("saleArea", getSaleArea())
                .append("status", getStatus())
                .toString();
    }
}
