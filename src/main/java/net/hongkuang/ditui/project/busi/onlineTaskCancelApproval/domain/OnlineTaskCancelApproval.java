package net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 任务取消审批表 busi_online_task_cancel_approval
 *
 * @author:zy
 * @date: 2019/4/22
 */
public class OnlineTaskCancelApproval extends BaseEntity {
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
    private String employeeId;
    /**
     * 业务员手机号
     */
    private String employeePhone;
    /**
     * 业务员姓名
     */
    private String employeeName;
    /**
     * 业务员所在地区
     */
    private String employeeArea;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeArea() {
        return employeeArea;
    }

    public void setEmployeeArea(String employeeArea) {
        this.employeeArea = employeeArea;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OnlineTaskCancelApproval{" +
                "id=" + id +
                ", approvalId='" + approvalId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", remark='" + remark + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeArea='" + employeeArea + '\'' +
                ", status=" + status +
                '}';
    }
}
