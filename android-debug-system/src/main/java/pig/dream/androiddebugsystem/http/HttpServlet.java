package pig.dream.androiddebugsystem.http;

import java.io.IOException;

/**
 * Created by zhukun on 2017/4/6.
 */

public class HttpServlet implements IHttpServlet {
    protected HttpContext httpContext;

    @Override
    public void init(HttpContext httpContext) {
        this.httpContext = httpContext;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {

    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }

    @Override
    public void destory(HttpContext httpContext) {
        this.httpContext = null;
    }
}
