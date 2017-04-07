package pig.dream.androiddebugsystem.http;

import java.io.IOException;

/**
 * Created by zhukun on 2017/4/5.
 */

public interface IHttpServlet {
    void init(HttpContext httpContext);
    void start(HttpRequest request, HttpResponse response) throws IOException;
    void doGet(HttpRequest request, HttpResponse response) throws IOException;
    void doPost(HttpRequest request, HttpResponse response) throws IOException;
    void destory(HttpContext httpContext);
}
