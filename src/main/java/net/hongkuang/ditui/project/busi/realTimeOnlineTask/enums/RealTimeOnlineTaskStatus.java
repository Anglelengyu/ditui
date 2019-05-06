package net.hongkuang.ditui.project.busi.realTimeOnlineTask.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/4/18
 */
public enum RealTimeOnlineTaskStatus {
    DISRUSH(1, "未抢"), RUSH(2, "已抢");

    private final Integer code;
    private final String info;

    RealTimeOnlineTaskStatus(Integer code, String info) {
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
