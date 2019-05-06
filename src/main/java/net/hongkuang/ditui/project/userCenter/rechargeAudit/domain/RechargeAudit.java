package net.hongkuang.ditui.project.userCenter.rechargeAudit.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.support.AuditStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 充值审核表 sys_recharge_audit
 *
 * @author yj
 * @date 2019-04-17
 */
public class RechargeAudit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;

    private Integer packageId;

    /**
     * 充值金额
     */
    private Long price;
    /**
     * 本次充值
     */
    private Long baseCoin;
    /**
     * 本次赠送
     */
    private Long giveCoin;
    /**
     * 人员ID
     */
    private Integer empId;

    /**
     * 审核状态
     */
    private AuditStatus status;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setBaseCoin(Long baseCoin) {
        this.baseCoin = baseCoin;
    }

    public Long getBaseCoin() {
        return baseCoin;
    }

    public void setGiveCoin(Long giveCoin) {
        this.giveCoin = giveCoin;
    }

    public Long getGiveCoin() {
        return giveCoin;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("price", getPrice())
                .append("baseCoin", getBaseCoin())
                .append("giveCoin", getGiveCoin())
                .append("empId", getEmpId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
