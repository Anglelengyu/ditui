package net.hongkuang.ditui.project.busi.order.enums;

/**
 * 用户状态
 *
 * @author:zy
 * @date: 2019/4/12
 */
public enum TbTransactionOrderAllocatStatus {
    WAITBUILD(0, "待生成"), UNFINISHED(1, "未分配"), COMPLETE(2, "已分配"), HISTORY(3, "已迁移");

    private final Integer code;
    private final String info;

    TbTransactionOrderAllocatStatus(Integer code, String info) {
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
