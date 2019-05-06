package net.hongkuang.ditui.project.busi.order.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum TbTransactionOrderStatus {
    UNFINISHED(0, "未接单"), COMPLETE(1, "已接单"), FINISH(2, "已完成"), PLACEMENT(3, "订单取消");

    private final Integer code;
    private final String info;

    TbTransactionOrderStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
