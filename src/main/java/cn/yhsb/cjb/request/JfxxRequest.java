package cn.yhsb.cjb.request;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

import cn.yhsb.cjb.service.Data;
import cn.yhsb.cjb.service.JsonField;
import cn.yhsb.cjb.service.PageRequest;

public class JfxxRequest extends PageRequest {
    @SerializedName("aac002")
    String idcard = "";

    public JfxxRequest(String idcard) {
        super("executeSncbqkcxjfxxQ", 1, 500);
        this.idcard = idcard;
    }

    public static class Jfxx extends Data {
        /** 缴费年度 */
        @SerializedName("aae003")
        public Integer year;

        /** 备注 */
        @SerializedName("aae013")
        public String memo;

        /** 金额 */
        @SerializedName("aae022")
        public BigDecimal amount;

        /** 缴费类型 */
        public static class Type extends JsonField {
            @Override
            public String toString() {
                switch (value) {
                    case "10":
                        return "正常应缴";
                    case "31":
                        return "补缴";
                    default:
                        return "未知值: " + value;
                }
            }
        }

        @SerializedName("aaa115")
        public Type type;

        /** 缴费项目 */
        public static class Item extends JsonField {
            @Override
            public String toString() {
                switch (value) {
                    case "1":
                        return "个人缴费";
                    case "3":
                        return "省级财政补贴";
                    case "4":
                        return "市级财政补贴";
                    case "5":
                        return "县级财政补贴";
                    case "11":
                        return "政府代缴";
                    default:
                        return "未知值: " + value;
                }
            }
        }

        @SerializedName("aae341")
        public Item item;

        /** 缴费方式 */
        public static class Method extends JsonField {
            @Override
            public String toString() {
                switch (value) {
                    case "2":
                        return "银行代收";
                    case "3":
                        return "经办机构自收";
                    default:
                        return "未知值: " + value;
                }
            }
        }

        @SerializedName("aab033")
        public Method method;

        /** 划拨日期 */
        @SerializedName("aae006")
        public String paidOffDay;

        /** 是否已划拨 */
        public boolean paidOff() {
            return paidOffDay != null;
        }

        /** 社保机构 */
        @SerializedName("aaa027")
        public String agency;

        /** 行政区划代码 */
        @SerializedName("aaf101")
        public String xzqh;
    }
}