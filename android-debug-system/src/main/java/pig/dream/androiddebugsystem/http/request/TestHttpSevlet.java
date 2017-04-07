package pig.dream.androiddebugsystem.http.request;

import android.util.Log;

import java.io.IOException;

import pig.dream.androiddebugsystem.http.HttpRequest;
import pig.dream.androiddebugsystem.http.HttpResponse;
import pig.dream.androiddebugsystem.http.HttpServlet;
import pig.dream.androiddebugsystem.http.RoutePath;

/**
 * Created by zhukun on 2017/4/5.
 */

@RoutePath("/test")
public class TestHttpSevlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        Log.i("ADS", "Look....");

        byte[] content = getHtmlFromFile("abc.html");
        response.setHtmlContent(content);
        httpContext.tplName = "";
    }


    @Override
    public String toString() {
        return "TestHttpSevlet";
    }
}
