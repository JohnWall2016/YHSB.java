package cn.yhsb.base.util;

import java.io.PrintStream;

/**
 * IO
 */
public class IO {
    public static PrintStream println(String s) {
        System.out.println(s);
        return System.out;
    }

    public static PrintStream format(String format, Object ... args) {
        return System.out.format(format, args);
    }
}