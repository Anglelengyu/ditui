package net.hongkuang.ditui.project.busi.orderTemplate.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/2/28
 */
public enum OrderTemplateStatus {
    WAITREFER(1, "待提交"), WAITCHECK(2, "待审核"), ADOPTCHECK(3, "审核通过"), NOADOPTCHECK(4, "审核未通过"), WAITSPLIT(5, "待拆分"), SPLIT(6, "已拆分");

    private final Integer code;
    private final String info;

    OrderTemplateStatus(Integer code, String info) {
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
