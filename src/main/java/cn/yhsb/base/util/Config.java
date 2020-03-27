package cn.yhsb.base.util;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class Config {
    static Properties configures = new Properties();
    static {
        try (var input = new InputStreamReader(
                Files.newInputStream(Paths.get("config.properties")),
                Charset.forName("UTF-8"))) {
            configures.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return configures.getProperty(key);
    }

    final static String splitString = ";";
    final static String matchString = ":";
    final static String wildcardString = "*";
    final static String hyphenString = "-";

    static HashMap<String, HashMap<String, String>> mapCache = new HashMap<>();

    static HashMap<String, String> loadMap(String key) {
        var map = new HashMap<String, String>();
        var mapString = getValue(key);
        if (mapString != null) {
            var pairs = mapString.split(splitString);
            for (var pair : pairs) {
                var pars = pair.split(matchString);
                if (pars.length == 2 && !pars[0].equals("")) {
                    // System.out.println(pars[0] + "::" + pars[1]);
                    map.put(pars[0], pars[1]);
                }
            }
        }
        mapCache.put(key, map);
        return map;
    }

    public static String getMapValue(String key, String... subKeys) {
        HashMap<String, String> map;
        if (mapCache.containsKey(key)) {
            map = mapCache.get(key);
        } else {
            map = loadMap(key);
        }

        var subKey = String.join(hyphenString, subKeys);
        if (map.containsKey(subKey)) {
            return map.get(subKey);
        } else {
            var len = subKeys.length;
            for (var i = 1; i < len; i++) {
                subKey = String.join(hyphenString,
                        Arrays.copyOf(subKeys, len - i));
                subKey = String.join(hyphenString, subKey, wildcardString);
                if (map.containsKey(subKey)) {
                    return map.get(subKey);
                }
            }
            return map.get(wildcardString);
        }
    }
}