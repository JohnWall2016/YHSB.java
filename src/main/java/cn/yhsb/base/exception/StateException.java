package cn.yhsb.base.exception;

/**
 * StateException
 */
public class StateException extends BaseException {
    private static final long serialVersionUID = 1L;

    public StateException(String message) {
        super(message);
    }

    public StateException(Exception e) {
        super(e);
    }
}