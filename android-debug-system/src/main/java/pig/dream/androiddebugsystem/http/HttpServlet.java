package pig.dream.androiddebugsystem.http;

import java.io.IOException;

/**
 * Created by zhukun on 2017/4/5.
 */

public interface HttpServlet {
    void doGet(HttpRequest request, HttpResponse response) throws IOException;
    void doPost(HttpRequest request, HttpResponse response) throws IOException;
}
