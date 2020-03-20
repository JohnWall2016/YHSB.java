package cn.yhsb.base.network;

import java.io.ByteArrayOutputStream;

public class HttpRequest {
    final private HttpHeader header = new HttpHeader();
    final private ByteArrayOutputStream body = new ByteArrayOutputStream(512);

    final private String path;
    final private String method;

    private String charset;

    public HttpRequest(String path, String method) {
        this.path = path;
        this.method = method;
        this.charset = "UTF-8";
    }

    public void addHeader(HttpHeader header) {
        header.addAll(header);
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public HttpRequest addHeader(String name, String value) {
        header.add(name, value);
        return this;
    }

    public HttpRequest addBody(String content) {
        try {
            body.write(content.getBytes(charset));
        } catch (Exception e) {
            throw new HttpException(e);
        }
        return this;
    }

    public byte[] getBytes() {
        var buffer = new ByteArrayOutputStream(512);
        try {
            buffer.write((method + " " + path + " HTTP/1.1\r\n").getBytes(charset));

            for (var entry : header) {
                buffer.write((entry.getKey() + ": " + entry.getValue() + "\r\n").getBytes(charset));
            }

            if (body.size() > 0) {
                buffer.write(("content-length: " + body.size() + "\r\n").getBytes(charset));
            }

            buffer.write("\r\n".getBytes(charset));

            if (body.size() > 0) {
                body.writeTo(buffer);
            }
        } catch (Exception e) {
            throw new HttpException(e);
        }

        return buffer.toByteArray();
    }
}