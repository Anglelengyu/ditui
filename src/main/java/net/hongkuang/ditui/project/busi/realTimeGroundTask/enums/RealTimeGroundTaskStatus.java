package net.hongkuang.ditui.project.busi.realTimeGroundTask.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/4/18
 */
public enum RealTimeGroundTaskStatus {
    DISRUSH(1, "未抢"), RUSH(2, "已抢");

    private final Integer code;
    private final String info;

    RealTimeGroundTaskStatus(Integer code, String info) {
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
