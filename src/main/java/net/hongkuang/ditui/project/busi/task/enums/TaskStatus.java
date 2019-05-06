package net.hongkuang.ditui.project.busi.task.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/2/28
 */
public enum TaskStatus {
    UNFINISHED(1, "未接单"), COMPLETE(2, "已接单"), PLACEMENT(3, "取消中"), FINISH(4, "已完成");

    private final Integer code;
    private final String info;

    TaskStatus(Integer code, String info) {
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
