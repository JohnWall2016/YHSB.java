package cn.yhsb.base.util;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    public static <T> Stream<T> fromIterator(Iterator<T> it) {
        var spliterator = Spliterators.spliteratorUnknownSize(it, 0);
        return StreamSupport.stream(spliterator, false);
    }
}