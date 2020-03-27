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

    public static String getDBDriver() {
        return Config.getValue("db_driver");
    }

    public static String getDBDialect() {
        return Config.getValue("db_dialect");
    }

    public static String getDBUrl() {
        return Config.getValue("db_url");
    }

    public static String getDBUser() {
        return Config.getValue("db_user");
    }

    public static String getDBPwd() {
        return Config.getValue("db_pwd");
    }
}