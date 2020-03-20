package cn.yhsb.base.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.yhsb.base.util.Tuple;

public class HttpHeader implements Iterable<Tuple<String, String>> {
    final private Map<String, List<String>> header = new HashMap<String, List<String>>();

    public List<String> get(String key) {
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
            add(entry.first, entry.second);
        }
    }

    public boolean containsKey(String name) {
        return header.containsKey(name);
    }

    public List<String> remove(String name) {
        return header.remove(name);
    }

    public void clear() {
        header.clear();
    }

    @Override
    public Iterator<Tuple<String, String>> iterator() {
        return header.entrySet().stream().flatMap(e -> {
            var key = e.getKey();
            return e.getValue().stream().map(v -> new Tuple<>(key, v));
        }).iterator();
    }
}