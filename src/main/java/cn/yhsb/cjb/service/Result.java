package cn.yhsb.cjb.service;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Result<T extends Data> extends Data {
    int rowcount, page, pagesize;
    String serviceid, type, vcode, message, messagedetail;

    ArrayList<T> datas = new ArrayList<T>();

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

    public ArrayList<T> getDatas() {
        return datas;
    }

    public boolean empty() {
        if (datas == null || datas.size() <= 0)
            return true;
        else
            return false;
    }

    public static <T extends Data> Result<T> fromJson(String json, Class<T> datasType) {
        var type = TypeToken.getParameterized(Result.class, datasType).getType();
        return new Gson().<Result<T>>fromJson(json, type);
    }
}