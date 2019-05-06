package net.hongkuang.ditui.project.busi.employee.support;

public enum EmployeeKeyEnum {
    ONLINE("online_employee", "线上"), GROUND("ground_employee", "地推");
    private final String code;
    private final String msg;

    EmployeeKeyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
