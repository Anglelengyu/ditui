package net.hongkuang.ditui.common.constant;

public enum QinChengEnum {
    SUCCESS(0, "操作成功"),
    FAILURE(1, "操作失败"),
    NOT_SUFFICIENT_FUNDS(2, "余额不足"),
    MOBILES_IS_NULL(3, "没有要发送的号码"),
    CREATE_ACTIVITY_FAILURE(4, "创建活动失败"),

    INVALID_IP(2003, "无效的IP"),
    MISSING_PARAMETERS(2004, "缺少参数"),
    UNAUTHORIZED_USER(2005, "未购买软件或者未授权"),
    INVALID_BODY(2006, "responseBody 无效文本，请检查加密程序");
    private final Integer code;
    private final String msg;

    QinChengEnum(Integer code, String msg) {
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
