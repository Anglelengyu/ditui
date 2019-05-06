package net.hongkuang.ditui.project.busi.order.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum TaskTaskStatus {
    WAIT(1, "未接单"), RECEIVED(2, "已接单"), CANCEL(3, "取消中"), COMPLETED(4, "已完成");

    private final Integer code;
    private final String info;

    TaskTaskStatus(Integer code, String info) {
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
