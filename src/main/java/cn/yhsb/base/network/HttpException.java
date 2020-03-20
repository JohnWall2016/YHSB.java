package cn.yhsb.base.network;

/**
 * HttpException
 */
public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(Exception e) {
        super(e);
    }
}