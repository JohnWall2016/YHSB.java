package cn.yhsb.base.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.yhsb.base.util.Tuple;

public class HttpHeader implements Iterable<Tuple<String, String>> {
    final private Map<String, List<String>> header = new HashMap<String, List<String>>();

    public List<String> get(String key) {
        return header.get(key);
    }

    public void put(String key, List<String> value) {
        header.put(key, value);
    }

    public boolean containsKey(String key) {
        return header.containsKey(key);
    }

    public void add(String name, String value) {
        var key = name.toLowerCase();
        if (!header.containsKey(key)) {
            header.put(key, List.of(value));
        } else {
            header.get(key).add(value);
        }
    }

    public void add(HttpHeader header) {
        for (var entry : header) {
            add(entry.first, entry.second);
        }
    }

    @Override
    public Iterator<Tuple<String, String>> iterator() {
        return header.entrySet().stream().flatMap(e -> {
            var key = e.getKey();
            return e.getValue().stream().map(v -> new Tuple<>(key, v));
        }).iterator();
    }
}