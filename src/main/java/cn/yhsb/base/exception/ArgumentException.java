package cn.yhsb.base.exception;

/**
 * ArgumentException
 */
public class ArgumentException extends BaseException {
    
    private static final long serialVersionUID = 1L;

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(Exception e) {
        super(e);
    }
    
}