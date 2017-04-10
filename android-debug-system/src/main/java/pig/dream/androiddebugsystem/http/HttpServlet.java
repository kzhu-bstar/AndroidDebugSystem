package pig.dream.androiddebugsystem.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import pig.dream.androiddebugsystem.base.Timer;
import pig.dream.androiddebugsystem.utils.ClosableUtils;

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
    public void start(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getMethod().toUpperCase().equals("GET")) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }

        // 模版渲染
        rendering(response);
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

    protected byte[] getHtmlFromFile(String file) {
        Log.i("ADS", "getHtmlFromFile " + file);
        file = getAsssetFilePathFromUri(file);
        InputStream is = null;
        try {
            is = httpContext.context.getAssets().open(file);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer, 0, len);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ClosableUtils.close(is);
        }

        return null;
    }

    private String getAsssetFilePathFromUri(String uri) {
        if (uri != null && uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        }
        return uri;
    }

    private void rendering(HttpResponse response) throws IOException {
        if (TextUtils.isEmpty(httpContext.tplName)) {
            return ;
        }

        String content = new String(getHtmlFromFile(httpContext.tplName));
        Log.i("ADS", "httpContext data size" + httpContext.data.size());
//        String result = BeetlHelper.render(content, httpContext.data);
//        String result = FreemarkerHelper.render(content, httpContext.data);
        Timer.init();
        String result = AtlHelper.render(content, httpContext.data);
        Timer.out("Render time: ");
        if (TextUtils.isEmpty(result)) {
            return;
        }
        response.setHtmlContent(result.getBytes());
    }

}
