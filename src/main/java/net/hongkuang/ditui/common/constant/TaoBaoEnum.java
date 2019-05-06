package net.hongkuang.ditui.common.constant;

public enum TaoBaoEnum {

    TRADE_FINISHED("TRADE_FINISHED", "交易完成"),
    WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS", "等待买家发货"),
    TRADE_NO_CREATE_PAY("TRADE_NO_CREATE_PAY", "没有创建支付宝交易"),
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "买家未付款");


    public TaoBaoEnum getEnum(String status) {
        for (TaoBaoEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    TaoBaoEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private String status;
    private String msg;

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
}
