package net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/4/22
 */
public enum OnlineTaskCancelApprovalStatus {
    WAIT(1, "待审核"), ENTRY(2, "审核通过"), REFUSE(3, "驳回");

    private final Integer code;
    private final String info;

    OnlineTaskCancelApprovalStatus(Integer code, String info) {
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
