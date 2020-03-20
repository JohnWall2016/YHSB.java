package cn.yhsb.cjb.service;

import java.util.List;
import java.util.Objects;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class JsonService<T extends Request> {
    @SerializedName("serviceid")
    String serviceID = "";

    String target = "";

    @SerializedName("sessionid")
    String sessionID;

    @SerializedName("loginname")
    String loginName;

    String password;

    T params;
    List<T> datas;

    public String getLoginName() {
        return loginName;
    }

    public JsonService<T> setLoginName(String loginname) {
        this.loginName = loginname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JsonService<T> setPassword(String password) {
        this.password = password;
        return this;
    }

    public JsonService(T params) {
        Objects.requireNonNull(params);
        this.serviceID = params.id();
        this.params = params;
        this.datas = List.of(params);
    }

    public static JsonService<CustomRequest> withoutParams(String serviceId) {
        return new JsonService<>(new CustomRequest(serviceId));
    }

    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}