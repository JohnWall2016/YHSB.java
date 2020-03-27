package cn.yhsb.base.exception;

/**
 * BaseException
 */
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Exception e) {
        super(e);
    }
}