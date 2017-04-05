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
public class TestHttpSevlet implements HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        Log.i("ADS", "Look....");

//        response.setHtmlContent("Hello world, hahah................");

        response.setHtmlFile("abc.html");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }

    @Override
    public String toString() {
        return "TestHttpSevlet";
    }
}
