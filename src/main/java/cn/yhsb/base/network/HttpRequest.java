package cn.yhsb.base.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpRequest {
    final private HttpHeader header = new HttpHeader();
    final private ByteArrayOutputStream body = new ByteArrayOutputStream(512);

    final private String path;
    final private String method;
    final private String charset;

    public HttpRequest(String path, String method, HttpHeader header, String charset) {
        this.path = path;
        this.method = method;
        if (header != null)
            this.header.addAll(header);
        if (charset != null)
            this.charset = charset;
        else
            this.charset = "UTF-8";
    }

    public static HttpRequest createGetRequest(String path) {
        return new HttpRequest(path, "GET", null, null);
    }

    public HttpRequest addHeader(String name, String value) {
        header.add(name, value);
        return this;
    }

    public HttpRequest addBody(String content) throws UnsupportedEncodingException, IOException {
        body.write(content.getBytes(charset));
        return this;
    }

    public byte[] getBytes() throws UnsupportedEncodingException, IOException {
        var buffer = new ByteArrayOutputStream(512);
        buffer.write((method + " " + path + " HTTP/1.1\r\n").getBytes(charset));

        for (var entry: header) {
            buffer.write((entry.first + ": " + entry.second + "\r\n").getBytes(charset));
        }

        if (body.size() > 0) {
            buffer.write(("content-length: " + body.size() + "\r\n").getBytes(charset));
        }

        buffer.write("\r\n".getBytes(charset));

        if (body.size() > 0) {
            body.writeTo(buffer);
        }

        return buffer.toByteArray();
    }
}