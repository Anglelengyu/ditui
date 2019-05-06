package net.hongkuang.ditui.project.busi.onlineEmployeeOrder.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/4/19
 */
public enum OnlineEmployeeOrderStatus {
    UNFINISHED(1, "未完成"), COMPLETE(2, "已完成"), PLACEMENT(3, "已接单"), CANCEL(4, "已取消");

    private final Integer code;
    private final String info;

    OnlineEmployeeOrderStatus(Integer code, String info) {
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
