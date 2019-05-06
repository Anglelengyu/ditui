package net.hongkuang.ditui.project.busi.salesman.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum SalemenStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    SalemenStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
