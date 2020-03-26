package cn.yhsb.cjb.service;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {
    private static final GsonBuilder builder;
    private static final Gson gson;

    static {
        builder = new GsonBuilder().serializeNulls();
        // builder.registerTypeAdapter(JsonField.class, new JsonField.Adapter());
        builder.registerTypeHierarchyAdapter(JsonField.class, new JsonField.Adapter());
        gson = builder.create();
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type typeOfT) {
        return (T)gson.fromJson(json, typeOfT);
    }
/*
    public static <T> T fromJson(String json, Class<T> typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
*/
}