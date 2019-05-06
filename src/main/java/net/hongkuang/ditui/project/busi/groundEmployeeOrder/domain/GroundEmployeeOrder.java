package net.hongkuang.ditui.project.busi.groundEmployeeOrder.domain;

import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 业务员订单表 busi_ground_employee_order
 *
 * @author:zy
 * @date: 2019/4/19
 */
public class GroundEmployeeOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String groundEmployeeOrderId;
    /**
     * 线下员工id
     */
    private String employeeId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 状态1已完成2未完成3取消中4已取消
     */
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;

    /**
     * 团队ID
     */
    private Long teamId;

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

    public String getGroundEmployeeOrderId() {
        return groundEmployeeOrderId;
    }

    public void setGroundEmployeeOrderId(String groundEmployeeOrderId) {
        this.groundEmployeeOrderId = groundEmployeeOrderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
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
                .append("orderId", getOrderId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .toString();
    }

    public static GroundEmployeeOrder build(String orderId, String employeeId) {
        GroundEmployeeOrder saleOrder = new GroundEmployeeOrder();
        saleOrder.setStatus(1);
        saleOrder.setGroundEmployeeOrderId(RandomUtil.genString());
        saleOrder.setOrderId(orderId);
        saleOrder.setEmployeeId(employeeId);
        return saleOrder;
    }
}
