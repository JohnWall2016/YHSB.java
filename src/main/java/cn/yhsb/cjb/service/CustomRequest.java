package cn.yhsb.cjb.service;

public class CustomRequest implements Request {
    private transient String id;

    @Override
    public String id() {
        return id;
    }

    public CustomRequest(String id) {
        this.id = id;
    }
}