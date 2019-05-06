package net.hongkuang.ditui.project.userCenter.employeeAccount.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 用户账户表 sys_employee_account
 *
 * @author yj
 * @date 2019-04-18
 */
public class EmployeeAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 当前总数
     */
    private Long currentPrice;
    /**
     * 历史消费
     */
    private Long rechargePrice;
    /**
     * 历史总数
     */
    private Long countPrice;
    /**
     * 当前用户
     */
    private Long empId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setRechargePrice(Long rechargePrice) {
        this.rechargePrice = rechargePrice;
    }

    public Long getRechargePrice() {
        return rechargePrice;
    }

    public void setCountPrice(Long countPrice) {
        this.countPrice = countPrice;
    }

    public Long getCountPrice() {
        return countPrice;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getEmpId() {
        return empId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("currentPrice", getCurrentPrice())
                .append("rechargePrice", getRechargePrice())
                .append("countPrice", getCountPrice())
                .append("empId", getEmpId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
