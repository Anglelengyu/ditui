package net.hongkuang.ditui.project.busi.task.dto;

import java.math.BigDecimal;

/**
 * Created by apple on 2019/1/19.
 */
public class CheckOrderResult {
    private String orderId;
    private Integer status;
    private String buyerNick;
    private String msg;
    private String tid;
    private BigDecimal payment;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }


    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Override
    public String toString() {
        return "CheckOrderResult{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", buyerNick='" + buyerNick + '\'' +
                ", msg='" + msg + '\'' +
                ", tid='" + tid + '\'' +
                ", payment=" + payment +
                '}';
    }
}
