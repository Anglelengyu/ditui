package net.hongkuang.ditui.common.exception;

/**
 * 业务异常
 *
 * @author ruoyi
 */
public class BusinessDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final Integer code;
    protected final String message;
    protected final Object data;

    public BusinessDataException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Integer getCode() {
        return code;
    }
}
