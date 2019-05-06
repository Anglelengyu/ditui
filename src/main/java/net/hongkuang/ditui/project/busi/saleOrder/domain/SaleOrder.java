package net.hongkuang.ditui.project.busi.saleOrder.domain;

import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 业务员订单表 busi_sale_order
 *
 * @author yj
 * @date 2019-01-11
 */
public class SaleOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String saleOrderId;
    /**
     * 业务人员id
     */
    private String saleId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleId() {
        return saleId;
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
                .append("saleOrderId", getSaleOrderId())
                .append("saleId", getSaleId())
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

    public static SaleOrder build(String orderId, String saleId) {
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setStatus(1);
        saleOrder.setSaleOrderId(RandomUtil.genString());
        saleOrder.setOrderId(orderId);
        saleOrder.setSaleId(saleId);
        return saleOrder;
    }
}
