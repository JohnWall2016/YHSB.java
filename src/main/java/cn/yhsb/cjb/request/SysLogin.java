package cn.yhsb.cjb.request;

import com.google.gson.annotations.SerializedName;

import cn.yhsb.cjb.service.CustomRequest;

public class SysLogin extends CustomRequest {
    @SerializedName("username")
    String userName; 
    
    @SerializedName("passwd")
    String password;

    public SysLogin(String userName, String password) {
        super("syslogin");
        this.userName = userName;
        this.password = password;
    }
}