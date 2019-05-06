package net.hongkuang.ditui.project.busi.realTimeTask.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum RealTimeTaskStatus {
    DISRUSH(1, "未抢"), RUSH(2, "已抢");

    private final Integer code;
    private final String info;

    RealTimeTaskStatus(Integer code, String info) {
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
