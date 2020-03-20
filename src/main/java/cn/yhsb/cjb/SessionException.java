package cn.yhsb.cjb;

/**
 * SessionException
 */
public class SessionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SessionException(Exception e) {
        super(e);
    }
}