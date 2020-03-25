package cn.yhsb.cjb.request;

import com.google.gson.annotations.SerializedName;
import cn.yhsb.cjb.service.CustomRequest;

/**
 * 省内参保信息查询
 */
public class CbxxRequest extends CustomRequest {
    @SerializedName("aac002")
    public String idcard = "";

    public CbxxRequest(String idcard) {
        super("executeSncbxxConQ");
        this.idcard = idcard;
    }

    public static class Cbxx {
        
    }
}