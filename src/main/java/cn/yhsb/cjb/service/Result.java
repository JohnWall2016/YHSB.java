package cn.yhsb.cjb.service;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class Result<T extends Data> extends Data {
    int rowcount, page, pagesize;
    String serviceid, type, vcode, message, messagedetail;

    @SerializedName("datas")
    ArrayList<T> data = new ArrayList<T>();

    public int getRowcount() {
        return rowcount;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public String getServiceid() {
        return serviceid;
    }

    public String getType() {
        return type;
    }

    public String getVcode() {
        return vcode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessagedetail() {
        return messagedetail;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public boolean empty() {
        if (data == null || data.size() <= 0)
            return true;
        else
            return false;
    }

    public static <T extends Data> Result<T> fromJson(String json, Class<T> clazz) {
        var type = TypeToken.getParameterized(Result.class, clazz).getType();
        return Data.<Result<T>>fromJson(json, type);
    }
}