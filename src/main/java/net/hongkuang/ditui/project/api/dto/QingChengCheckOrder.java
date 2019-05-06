package net.hongkuang.ditui.project.api.dto;

import java.math.BigDecimal;

public class QingChengCheckOrder {
    private String tid;
    private BigDecimal payment;
    private String buyer_nick;
    private String msg;
    private String status;

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }


    public BigDecimal getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    @Override
    public String toString() {
        return "QingChengCheckOrder{" +
                "tid='" + tid + '\'' +
                ", payment=" + payment +
                ", buyer_nick='" + buyer_nick + '\'' +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
