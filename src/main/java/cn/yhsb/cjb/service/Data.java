package cn.yhsb.cjb.service;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {
    private static final GsonBuilder builder;
    private static final Gson gson;

    static {
        builder = new GsonBuilder().serializeNulls();
        builder.registerTypeAdapter(JsonField.class, JsonField.Adapter.class);
        gson = builder.create();
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}