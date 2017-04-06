package pig.dream.androiddebugsystem.http.request;

import android.text.TextUtils;

import java.io.IOException;

import pig.dream.androiddebugsystem.http.HttpCode;
import pig.dream.androiddebugsystem.http.HttpContentType;
import pig.dream.androiddebugsystem.http.HttpRequest;
import pig.dream.androiddebugsystem.http.HttpResponse;
import pig.dream.androiddebugsystem.http.HttpServlet;

/**
 * Created by zhukun on 2017/4/5.
 */

public class StaticFileHttpSevlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String vaule = (String) request.getHeader("If-Modified-Since");
        if (!TextUtils.isEmpty(vaule)) {
            response.setStatuCode(HttpCode.HTTP_NOT_MODIFIED);
            return;
        }

        String uri = request.getUri();
        response.setContentType(HttpContentType.ContentTypeByFileName(uri));
        response.setHtmlFile(uri);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }

    @Override
    public String toString() {
        return "StaticFileHttpSevlet";
    }
}
