package pig.dream.androiddebugsystem.http.request;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

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

        response.setHtmlFile("abc.html");
    }


    @Override
    public String toString() {
        return "TestHttpSevlet";
    }
}
