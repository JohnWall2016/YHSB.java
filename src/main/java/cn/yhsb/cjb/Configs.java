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
}