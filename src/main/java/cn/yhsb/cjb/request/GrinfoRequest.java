package cn.yhsb.cjb.request;

import com.google.gson.annotations.SerializedName;
import cn.yhsb.cjb.Configs;
import cn.yhsb.cjb.service.Data;
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

    public static class Grinfo extends Data {
        /** 个人编号 */
        @SerializedName("aac001")
        int grbh;

        /** 身份证号码 */
        @SerializedName("aac002")
        String idcard;

        @SerializedName("aac003")
        String name;

        @SerializedName("aac006")
        int birthday;

        /** 参保状态: "1"-正常参保 "2"-暂停参保 "4"-终止参保 "0"-未参保 */
        @SerializedName("aac008")
        String cbzt;

        /** 户口所在地 */
        @SerializedName("aac010")
        String hkszd;

        /** 缴费状态: "1"-参保缴费 "2"-暂停缴费 "3"-终止缴费 */
        @SerializedName("aac031")
        String jfzt;

        @SerializedName("aae005")
        String phone;

        @SerializedName("aae006")
        String address;

        @SerializedName("aae010")
        String bankcard;

        /** 村组行政区划编码 */
        @SerializedName("aaf101")
        String czqh;

        /** 村组名称 */
        @SerializedName("aaf102")
        String czmc;

        /** 村社区名称 */
        @SerializedName("aaf103")
        String csmc;

        public int getGrbh() {
            return grbh;
        }

        public String getIdcard() {
            return idcard;
        }

        public String getName() {
            return name;
        }

        public int getBirthday() {
            return birthday;
        }

        public String getCbzt() {
            return cbzt;
        }

        public String getHkszd() {
            return hkszd;
        }

        public String getJfzt() {
            return jfzt;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getBankcard() {
            return bankcard;
        }

        public String getCzqh() {
            return czqh;
        }

        public String getCzmc() {
            return czmc;
        }

        public String getCsmc() {
            return csmc;
        }

        public String getJbztCN() {
            return Configs.getJbztCN(this.jfzt, this.cbzt);
        }

        /**
         * @return 所属单位名称
         */
        public String getDwmc() {
            return Configs.getXzhqCN(this.czqh.substring(0, 8));
        }
    }
}
