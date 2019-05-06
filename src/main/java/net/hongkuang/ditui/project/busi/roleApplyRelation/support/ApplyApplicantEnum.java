package net.hongkuang.ditui.project.busi.roleApplyRelation.support;

public enum ApplyApplicantEnum {
    SHOP_MANAGER(1, "掌柜"), TEAM(2, "团队"), EMPLOYEE(3, "员工");
    private final Integer code;
    private final String msg;

    ApplyApplicantEnum(Integer code, String msg) {
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
