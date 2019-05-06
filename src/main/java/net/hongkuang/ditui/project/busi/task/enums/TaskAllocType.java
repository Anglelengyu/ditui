package net.hongkuang.ditui.project.busi.task.enums;

/**
 * Created by apple on 2019/1/14.
 */
public enum TaskAllocType {

    REAL_TIME(1, "实时分配"), BACKUP(2, "储备任务");

    private final Integer code;
    private final String info;

    TaskAllocType(Integer code, String info) {
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
