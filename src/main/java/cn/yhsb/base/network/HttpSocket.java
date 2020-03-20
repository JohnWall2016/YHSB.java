package cn.yhsb.base.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class HttpSocket implements AutoCloseable {
    private String host;

    public String getHost() {
        return host;
    }

    private int port;

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return getHost() + ":" + getPort();
    }

    private String charset = "UTF-8";

    public String getCharset() {
        return charset;
    }

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public HttpSocket(String host, int port, String charset) {
        this.host = host;
        this.port = port;
        this.charset = charset;
        try {
            socket = new Socket(host, port);
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public HttpSocket(String host, int port) {
        this(host, port, "UTF-8");
    }

    @Override
    public void close() {
        try {
            if (output != null) {
                output.close();
                output = null;
            }
            if (input != null) {
                input.close();
                input = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public void write(String content) {
        try {
            write(content.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e);
        }
    }

    public void write(byte[] bytes) {
        if (output == null)
            throw new HttpException("The output stream is closed");
        try {
            output.write(bytes);
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public String readLine() {
        if (input == null)
            throw new HttpException("The input stream is closed");
        try (var data = new ByteArrayOutputStream(512)) {
            int c = 0, n = 0;
            while (true) {
                c = input.read();
                if (c == -1)
                    return data.toString(charset);
                if (c == 0xd) {
                    n = input.read();
                    if (n == 0xa)
                        return data.toString(charset);
                    else if (n == -1) {
                        data.write(c);
                        return data.toString(charset);
                    } else {
                        data.write(c);
                        data.write(n);
                    }
                } else
                    data.write(c);
            }
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public HttpHeader readHeader() {
        var header = new HttpHeader();
        while (true) {
            var line = readLine();
            if (line == null || line.equals(""))
                break;
            var i = line.indexOf(':');
            if (i > 0) {
                header.add(line.substring(0, i).trim(), line.substring(i + 1).trim());
            }
        }
        return header;
    }

    void readToData(int len, InputStream input, OutputStream data) throws IOException {
        var b = new byte[len];
        var rlen = input.readNBytes(b, 0, len);
        if (rlen != len)
            throw new IllegalStateException("Length of data is shorter than expected");
        data.write(b);
    }

    public String readBody(HttpHeader header) {
        if (header == null)
            header = readHeader();
        try (var data = new ByteArrayOutputStream(512)) {
            if (header.get("Transfer-Encoding").contains("chunked")) {
                while (true) {
                    var len = Integer.parseInt(readLine(), 16);
                    if (len <= 0) {
                        readLine();
                        break;
                    }
                    readToData(len, input, data);
                    readLine();
                }
            } else if (header.containsKey("Content-Length")) {
                var len = Integer.parseInt(header.get("Content-Length").get(0), 10);
                if (len > 0) {
                    readToData(len, input, data);
                }
            } else {
                throw new UnsupportedOperationException("unsupported transfer mode");
            }
            return data.toString(charset);
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }

    public String readBody() {
        return readBody(null);
    }

    public String getHttp(String path) {
        var request = new HttpRequest(path, "GET");
        request.addHeader("Host", getUrl())
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "max-age=0")
            .addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent",
                       "Mozilla/5.0 (Windows NT 6.1; Win64; x64) " +
                       "AppleWebKit/537.36 (KHTML, like Gecko) " +
                       "Chrome/71.0.3578.98 " +
                       "Safari/537.36")
            .addHeader("Accept",
                       "text/html,applicationxhtml+xml,application/xml;"+
                       "q=0.9,image/webpimage/apng,*/*;q=0.8")
            .addHeader("Accept-Encoding", "gzip,deflate")
            .addHeader("Accept-Language", "zh-CN,zh;q=09");
        write(request.getBytes());
        return readBody();
    }
}
