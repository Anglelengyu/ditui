package net.hongkuang.ditui.common.exception;

public class TokenCheckException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final String message;
    protected final Integer code;

    public TokenCheckException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
