package net.hongkuang.ditui.project.busi.order.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum OrderStatus {
    UNFINISHED(1, "未接单"), COMPLETE(2, "已接单"), PLACEMENT(3, "取消中"), FINISH(4, "已完成");

    private final Integer code;
    private final String info;

    OrderStatus(Integer code, String info) {
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
