package cn.yhsb.base.util;

/**
 * ExcelException
 */
public class ExcelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcelException(String msg) {
        super(msg);
    }

    public ExcelException(Exception e) {
        super(e);
    }
}