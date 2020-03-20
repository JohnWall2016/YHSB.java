package cn.yhsb.base.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpHeader implements Iterable<Entry<String, String>> {
    final private Map<String, List<String>> header = new HashMap<>();

    public List<String> get(String key) {
        key = key.toLowerCase();
        if (header.containsKey(key)) {
            return header.get(key);
        }
        return List.of();
    }

    public void add(String name, String value) {
        var key = name.toLowerCase();
        if (!header.containsKey(key)) {
            header.put(key, new ArrayList<>());
        }
        header.get(key).add(value);
    }

    public void addAll(HttpHeader header) {
        for (var entry: header) {
            add(entry.getKey(), entry.getValue());
        }
    }

    public boolean containsKey(String name) {
        return header.containsKey(name.toLowerCase());
    }

    public List<String> remove(String name) {
        return header.remove(name.toLowerCase());
    }

    public void clear() {
        header.clear();
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        return header.entrySet().stream().flatMap(e -> {
            var key = e.getKey();
            return e.getValue().stream().map(v -> Map.entry(key, v));
        }).iterator();
    }
}