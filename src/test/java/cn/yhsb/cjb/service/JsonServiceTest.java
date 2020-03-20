package cn.yhsb.cjb.service;

import org.junit.Test;

import cn.yhsb.cjb.request.GrinfoRequest;

public class JsonServiceTest {

    @Test
    public void testCustomRequest() {
        System.out.println(JsonService.withoutParams("login"));
    }

    @Test
    public void testPageRequest() {
        System.out.println(new JsonService<>(new GrinfoRequest("12345678")));
    }
}