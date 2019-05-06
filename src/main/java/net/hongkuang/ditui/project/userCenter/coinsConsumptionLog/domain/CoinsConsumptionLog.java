package net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.support.ConsumptionLogStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 充值扣费记录表 sys_coins_consumption_log
 *
 * @author yj
 * @date 2019-04-17
 */
public class CoinsConsumptionLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
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
     * 表示充值还是扣除等状态
     */
    private ConsumptionLogStatus status;

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

    public void setStatus(ConsumptionLogStatus status) {
        this.status = status;
    }

    public ConsumptionLogStatus getStatus() {
        return status;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("price", getPrice())
                .append("baseCoin", getBaseCoin())
                .append("giveCoin", getGiveCoin())
                .append("empId", getEmpId())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
