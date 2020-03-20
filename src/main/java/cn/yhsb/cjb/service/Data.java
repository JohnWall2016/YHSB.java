package cn.yhsb.cjb.service;

import com.google.gson.GsonBuilder;

public class Data {
    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}