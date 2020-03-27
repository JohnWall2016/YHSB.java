package cn.yhsb.base.exception;

/**
 * OperationException
 */
public class OperationException extends BaseException {
    private static final long serialVersionUID = 1L;

    public OperationException(String message) {
        super(message);
    }

    public OperationException(Exception e) {
        super(e);
    }
}