package cn.yhsb.cjb.request;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import cn.yhsb.cjb.request.Fields.CbState;
import cn.yhsb.cjb.request.Fields.JbKind;
import cn.yhsb.cjb.request.Fields.JbState;
import cn.yhsb.cjb.request.Fields.JfState;
import cn.yhsb.cjb.service.CustomRequest;
import cn.yhsb.cjb.service.Data;

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

    public static class Cbxx extends Data implements JbState {
        /** 个人编号 */
        @SerializedName("aac001")
        int pid;

        /** 身份证号码*/
        @SerializedName("aac002")
        String idcard;

        @SerializedName("aac003")
        String name;

        @SerializedName("aac006")
        String birthDay;

        @SerializedName("aac008")
        CbState cbState;

        @SerializedName("aac031")
        JfState jfState;

        /** 参保时间*/
        @SerializedName("aac049")
        int cbDate;

        /** 参保身份编码*/
        @SerializedName("aac066")
        JbKind jbKind;

        /** 社保机构*/
        @SerializedName("aaa129")
        String agency;

        /** 经办时间*/
        @SerializedName("aae036")
        String dealDate;

        /** 行政区划编码*/
        @SerializedName("aaf101")
        String xzqhCode;

        /** 村组名称*/
        @SerializedName("aaf102")
        String czName;

        /** 村社区名称*/
        @SerializedName("aaf103")
        String csName;

        @Override
        public CbState getCbState() {
            return cbState;
        }

        @Override
        public JfState getJfState() {
            return jfState;
        }

        public boolean valid() {
            return !Strings.isNullOrEmpty(idcard);
        }

        public String getIdcard() {
            return idcard;
        }

        public String getName() {
            return name;
        }

        public JbKind getJbKind() {
            return jbKind;
        }

        public int getPid() {
            return pid;
        }

        public String getBirthDay() {
            return birthDay;
        }

        public int getCbDate() {
            return cbDate;
        }

        public String getAgency() {
            return agency;
        }

        public String getDealDate() {
            return dealDate;
        }

        public String getXzqhCode() {
            return xzqhCode;
        }

        public String getCzName() {
            return czName;
        }

        public String getCsName() {
            return csName;
        }
    }
}