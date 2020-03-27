package cn.yhsb.base.exception;

/**
 * UnsupportedException
 */
public class UnsupportedException  extends BaseException {
    private static final long serialVersionUID = 1L;

    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(Exception e) {
        super(e);
    }
}