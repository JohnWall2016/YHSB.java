package cn.yhsb.cjb.request;

import cn.yhsb.cjb.service.JsonField;

/**
 * 居保各种状态
 */
public class States {

    /**
     * 参保状态
     */
    public static class CbState extends JsonField {
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
    public static class JfState extends JsonField {
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
    public interface JbState {
        public CbState getCbState();

        public JfState getJfState();

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

}
