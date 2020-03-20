package cn.yhsb.cjb;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cn.yhsb.base.network.HttpException;
import cn.yhsb.base.network.HttpRequest;
import cn.yhsb.base.network.HttpSocket;
import cn.yhsb.cjb.request.SysLogin;
import cn.yhsb.cjb.service.Data;
import cn.yhsb.cjb.service.JsonService;
import cn.yhsb.cjb.service.Request;
import cn.yhsb.cjb.service.Result;

public class Session extends HttpSocket {
    private String userID;
    private String password;
    private HashMap<String, String> cookies = new HashMap<>();

    public Session(String host, int port, String userID, String password) {
        super(host, port);
        this.userID = userID;
        this.password = password;
    }

    private HttpRequest createRequest() {
        var request = new HttpRequest("/hncjb/reports/crud", "POST");
        var url = getUrl();
        request.addHeader("Host", url).addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("Origin", "http://" + url).addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")
                .addHeader("Content-Type", "multipart/form-data;charset=UTF-8")
                .addHeader("Referer", "http://" + url + "/hncjb/pages/html/index.html")
                .addHeader("Accept-Encoding", "gzip, deflate").addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        if (!cookies.isEmpty()) {
            var cookie = cookies.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(";"));
            request.addHeader("Cookie", cookie);
        }
        return request;
    }

    HttpRequest buildRequest(String content) {
        var request = createRequest();
        request.addBody(content);
        return request;
    }

    void request(String content) {
        var request = buildRequest(content);
        write(request.getBytes());
    }

    public String toService(Request request) {
        var service = new JsonService<>(request).setLoginName(userID).setPassword(password);
        return service.toString();
    }

    public void sendService(Request request) {
        request(toService(request));
    }

    public String toService(String id) {
        var service = JsonService.withoutParams(id).setLoginName(userID).setPassword(password);
        return service.toString();
    }

    public void sendService(String id) {
        request(toService(id));
    }

    public <T extends Data> Result<T> getResult(Class<T> datasType) {
        var result = readBody();
        return Result.fromJson(result, datasType);
    }

    public Result<Data> getResult() {
        return getResult(Data.class);
    }

    public String login() {
        sendService("loadCurrentUser");
        var header = readHeader();
        if (header.containsKey("set-cookie")) {
            header.get("set-cookie").forEach(cookie -> {
                var pattern = Pattern.compile("([^=]+?)=(.+?);");
                var matcher = pattern.matcher(cookie);
                if (matcher.find()) {
                    cookies.put(matcher.group(1), matcher.group(2));
                }
            });
        }
        readBody(header);

        sendService(new SysLogin(userID, password));
        return readBody();
    }

    public String logout() {
        sendService("syslogout");
        return readBody();
    }

    public static Session user002() {
        return new Session(Configs.getServerIP(), Integer.parseInt(Configs.getServerPort()), Configs.getUserId002(),
                Configs.getUserPwd002());
    }

    public interface Callable {
        public void call(Session session) throws Exception;
    }

    public static void user002(Callable call) {
        try (var session = Session.user002()) {
            session.login();
            call.call(session);
            session.logout();
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw new SessionException(e);
        }
    }
}