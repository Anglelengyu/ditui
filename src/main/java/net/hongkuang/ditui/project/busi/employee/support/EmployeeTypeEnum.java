package net.hongkuang.ditui.project.busi.employee.support;

public enum EmployeeTypeEnum {
    ONLINE(1, "线上"), GROUND(2, "地推");
    private final Integer code;
    private final String msg;

    EmployeeTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
