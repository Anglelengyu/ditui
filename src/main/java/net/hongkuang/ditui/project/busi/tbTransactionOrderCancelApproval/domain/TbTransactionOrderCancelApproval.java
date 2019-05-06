package net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;


/**
 * 订单取消审批表 busi_tb_transaction_order_cancel_approval
 *
 * @author:zy
 * @date: 2019/4/22
 */
public class TbTransactionOrderCancelApproval extends BaseEntity {
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
    private String orderId;
    /**
     * 取消原因
     */
    private String remark;
    /**
     * 员工id
     */
    private String employeeId;
    /**
     * 员工手机号
     */
    private String employeePhone;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 员工所在地区
     */
    private String employeeArea;
    /**
     * 审批状态1-未审批 2-通过 3-驳回
     */
    private Integer status;

    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
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
        return "TbTransactionOrderCancelApproval{" +
                "id=" + id +
                ", approvalId='" + approvalId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", remark='" + remark + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeArea='" + employeeArea + '\'' +
                ", status=" + status +
                '}';
    }
}
