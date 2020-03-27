package cn.yhsb.cjb.request;

import cn.yhsb.cjb.service.JsonField;

/**
 * 居保各种状态
 */
public class Fields {

    /**
     * 参保状态
     */
    public static class CBState extends JsonField {
        @Override
        public String getName() {
            switch (value) {
                case "0":
                    return "未参保";
                case "1":
                    return "正常参保";
                case "2":
                    return "暂停参保";
                case "4":
                    return "终止参保";
                default:
                    return "未知值: " + value;
            }
        }
    }

    /**
     * 缴费状态
     */
    public static class JFState extends JsonField {
        @Override
        public String getName() {
            switch (value) {
                case "1":
                    return "参保缴费";
                case "2":
                    return "暂停缴费";
                case "3":
                    return "终止缴费";
                default:
                    return "未知值: " + value;
            }
        }
    }

    /**
     * 居保状态
     */
    public interface JBState {
        public CBState getCbState();

        public JFState getJfState();

        default public String getJbState() {
            var jfState = getJfState().getValue();
            var cbState = getCbState().getValue();
            if (jfState == null || jfState.equals("0"))
                return "未参保";
            switch (jfState) {
                case "1": // 正常缴费
                    switch (cbState) {
                        case "1":
                            return "正常缴费人员";
                        default:
                            return "未知类型参保缴费人员: " + cbState;
                    }
                case "2": // 暂停缴费
                    switch (cbState) {
                        case "2":
                            return "暂停缴费人员";
                        default:
                            return "未知类型暂停缴费人员: " + cbState;
                    }
                case "3": // 终止缴费
                    switch (cbState) {
                        case "1":
                            return "正常待遇人员";
                        case "2":
                            return "暂停待遇人员";
                        case "4":
                            return "终止参保人员";
                        default:
                            return "未知类型终止缴费人员: " + cbState;
                    }
                default:
                    return "未知类型人员: " + jfState + ", " + cbState;
            }
        }
    }

    public static class JBKind extends JsonField {
        @Override
        public String getName() {
            switch (value) {
                case "011":
                    return "普通参保人员";
                case "021":
                    return "残一级";
                case "022":
                    return "残二级";
                case "031":
                    return "特困一级";
                case "051":
                    return "贫困人口一级";
                case "061":
                    return "低保对象一级";
                case "062":
                    return "低保对象二级";
                default:
                    return "未知身份类型: " + value;
            }
        }
    }

}
