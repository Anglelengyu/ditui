package net.hongkuang.ditui.project.busi.order.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum GroundTaskOrderStatus {
    NORMAL(1, "正常"), CANCEL(2, "取消");

    private final Integer code;
    private final String info;

    GroundTaskOrderStatus(Integer code, String info) {
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
