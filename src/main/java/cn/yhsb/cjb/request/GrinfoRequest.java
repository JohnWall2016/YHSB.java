package cn.yhsb.cjb.request;

import com.google.gson.annotations.SerializedName;

import cn.yhsb.cjb.service.PageRequest;

public class GrinfoRequest extends PageRequest {
    /** 行政区划编码 */
    @SerializedName("aaf013")
    String xzqh = "";

    /** 村级编码 */
    @SerializedName("aaz070")
    String cjbm = "";

    String aaf101 = "", aac009 = "";

    /** 参保状态: "1"-正常参保 "2"-暂停参保 "4"-终止参保 "0"-未参保 */ 
    @SerializedName("aac008")
    String cbzt = "";

    /** 缴费状态: "1"-参保缴费 "2"-暂停缴费 "3"-终止缴费 */
    @SerializedName("aac031")
    String jfzt = "";

    String aac006str = "", aac006end = "";
    String aac066 = "", aae030str = "";
    String aae030end = "", aae476 = "";

    @SerializedName("aac003")
    String name = "";

    /** 身份证号码 */
    @SerializedName("aac002")
    String idcard = "";

    String aae478 = "";

    public GrinfoRequest(String idcard) {
        super("zhcxgrinfoQuery");
        this.idcard = idcard;
    }
}