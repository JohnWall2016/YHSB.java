package cn.yhsb.cjb;


import cn.yhsb.base.util.Config;

public class Configs {
    
    public static String getServerIP() {
        return Config.getValue("hncjb_ip");
    }
    
    public static String getServerPort() {
        return Config.getValue("hncjb_port");
    }
    
    public static String getUserId002() {
        return Config.getValue("user002_id");
    }
    
    public static String getUserPwd002() {
        return Config.getValue("user002_pwd");
    }
    
    public static String getJbztCN(String jfzt, String cbzt) {
        return Config.getMapValue("jbzt_map", jfzt, cbzt);
    }
    
    public static String getXzhqCN(String code) {
        return Config.getMapValue("xzqh_map", code);
    }
    
}