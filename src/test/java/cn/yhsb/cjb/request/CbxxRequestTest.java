package cn.yhsb.cjb.request;

import org.junit.Test;

import cn.yhsb.cjb.request.CbxxRequest.Cbxx;
import cn.yhsb.cjb.service.Data;

/**
 * CbxxRequestTest
 */
public class CbxxRequestTest {
    @Test
    public void testCbxx() {
        var json = "{\"aac008\":\"4\"}";
        var cbxx = Data.getGson().fromJson(json, Cbxx.class);
        System.out.println(cbxx.getCbState());
    }
}