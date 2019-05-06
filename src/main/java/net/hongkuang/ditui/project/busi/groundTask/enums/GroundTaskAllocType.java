package net.hongkuang.ditui.project.busi.groundTask.enums;

/**
 * @author:zy
 * @date: 2019/4/19
 */
public enum GroundTaskAllocType {

    REAL_TIME(1, "实时分配"), BACKUP(2, "储备任务");

    private final Integer code;
    private final String info;

    GroundTaskAllocType(Integer code, String info) {
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
