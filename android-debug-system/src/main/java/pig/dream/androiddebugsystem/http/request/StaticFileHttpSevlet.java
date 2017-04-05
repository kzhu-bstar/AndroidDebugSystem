package pig.dream.androiddebugsystem.http.request;

import java.io.IOException;

import pig.dream.androiddebugsystem.http.HttpRequest;
import pig.dream.androiddebugsystem.http.HttpResponse;
import pig.dream.androiddebugsystem.http.HttpServlet;

/**
 * Created by zhukun on 2017/4/5.
 */

public class StaticFileHttpSevlet implements HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHtmlFile(request.getUri());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }

    @Override
    public String toString() {
        return "StaticFileHttpSevlet";
    }
}
