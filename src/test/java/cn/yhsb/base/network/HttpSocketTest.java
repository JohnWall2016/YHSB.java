package cn.yhsb.base.network;

import org.junit.Test;

public class HttpSocketTest {

    @Test
    public void testGetHttp() {
        try (var sock = new HttpSocket("124.228.42.248", 80)) {
            System.out.println(sock.getHttp("/"));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}