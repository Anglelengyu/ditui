package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;

/**
 * 淘宝问题订单表 busi_tb_transaction_question_order
 *
 * @author:zy
 * @date: 2019/4/26
 */
public class TbTransactionQuestionOrder extends TbTransactionOrder {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "问题订单ID")
    private Long questionOrderId;

    /**
     * 关联订单ID
     */
    private String orderId;

    /**
     * 问题描述
     */
    private String questionOrderRemark;

    /**
     * 操作角色
     */
    private String questionOrderRole;

    /**
     * 问题状态 0-待解决 1-已解决
     */
    private Integer questionOrderStatus;

    public String getQuestionOrderRemark() {
        return questionOrderRemark;
    }

    public void setQuestionOrderRemark(String questionOrderRemark) {
        this.questionOrderRemark = questionOrderRemark;
    }

    public Long getQuestionOrderId() {
        return questionOrderId;
    }

    public void setQuestionOrderId(Long questionOrderId) {
        this.questionOrderId = questionOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQuestionOrderRole() {
        return questionOrderRole;
    }

    public void setQuestionOrderRole(String questionOrderRole) {
        this.questionOrderRole = questionOrderRole;
    }

    public Integer getQuestionOrderStatus() {
        return questionOrderStatus;
    }

    public void setQuestionOrderStatus(Integer questionOrderStatus) {
        this.questionOrderStatus = questionOrderStatus;
    }
}
