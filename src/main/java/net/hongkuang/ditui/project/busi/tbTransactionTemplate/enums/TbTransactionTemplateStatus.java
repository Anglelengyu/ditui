package net.hongkuang.ditui.project.busi.tbTransactionTemplate.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/2/28
 */
public enum TbTransactionTemplateStatus {
    WAITREFER(0, "待提交"), WAITCHECK(1, "待审核"), ADOPTCHECK(2, "审核通过"), NOADOPTCHECK(3, "审核未通过"), WAITSPLIT(4, "待拆分"), SPLIT(5, "已拆分");

    private final Integer code;
    private final String info;

    TbTransactionTemplateStatus(Integer code, String info) {
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
