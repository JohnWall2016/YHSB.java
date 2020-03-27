package cn.yhsb.cjb.request;

import com.google.gson.annotations.SerializedName;

import cn.yhsb.cjb.service.Data;
import cn.yhsb.cjb.service.PageRequest;

/**
 * CbshRequest
 */
public class CbshRequest extends PageRequest {
    String aaf013 = "", aaf030 = "", aae011 = "", aae036 = "", aae036s = "",
            aae014 = "", aac009 = "", aac002 = "", aac003 = "", sfccb = "";

    @SerializedName("aae015")
    String startDate = ""; // "2020-03-27"

    @SerializedName("aae015s")
    String endDate = "";

    /** 审核状态 */
    @SerializedName("aae016")
    String state = "1";

    public CbshRequest(String startDate, String endDate, String state) {
        super("cbshQuery", 1, 500);
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    public CbshRequest(String startDate, String endDate) {
        this(startDate, endDate, "1");
    }

    public static class Cbsh extends Data {
        @SerializedName("aac002")
        String idcard;

        @SerializedName("aac003")
        String name;

        @SerializedName("aac006")
        String birthDay;

        public String getIdcard() {
            return idcard;
        }

        public String getName() {
            return name;
        }

        public String getBirthDay() {
            return birthDay;
        }
    }
}