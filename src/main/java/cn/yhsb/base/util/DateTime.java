package cn.yhsb.base.util;

import java.util.regex.Pattern;

import cn.yhsb.base.exception.ArgumentException;

/**
 * DateTime
 */
public class DateTime {
    public static String toDashedDate(String date, String dateFormat) {
        var p = Pattern.compile(dateFormat);
        var m = p.matcher(date);
        if (m.find()) {
            return m.group(1) + "-" + m.group(2) + "-" + m.group(3);
        }
        throw new ArgumentException("Invalid date format");
    }

    public static String toDashedDate(String date) {
        return toDashedDate(date, "^(\\d\\d\\d\\d)(\\d\\d)(\\d\\d)$");
    }
}