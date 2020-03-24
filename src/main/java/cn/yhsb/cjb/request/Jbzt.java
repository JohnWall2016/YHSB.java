package cn.yhsb.cjb.request;

import cn.yhsb.cjb.Configs;

/**
 * 居保状态
 */
public interface Jbzt {
    public String getCbzt();
    public String getJfzt();

    default public String getJbztCN() {
        return Configs.getJbztCN(getJfzt(), getCbzt());
    }
}