package pig.dream.androiddebugsystem.http;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhukun on 2017/4/1.
 */

public class RequestHandler {
    AssetManager assetManager;
    Context context;

    public RequestHandler(Context context) {
        this.context = context.getApplicationContext();
        this.assetManager = context.getAssets();
    }

    public void handle(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        String httpHeaders = readHttpHeaders(is);
        HttpRequest request = new HttpServletRequest(httpHeaders);
        String uri = request.getUri();
        Log.i("ADS", "HttpServletRequest uri " + uri + " Method " + request.getMethod());

        HttpResponse response = new HttpServletResponse();
        IHttpServlet httpServlet = HttpRoute.getInstance().getHttpServletByPath(uri);
        if (httpServlet == null) {
            Log.i("ADS", "----------" + uri);
            response.setStatuCode(HttpCode.HTTP_NOT_FOUND);
        } else {
            HttpContext httpContext = new HttpContext();
            httpContext.context = context;
            httpServlet.init(httpContext);
            if (request.getMethod().toUpperCase().equals("GET")) {
                httpServlet.doGet(request, response);
            } else {
                httpServlet.doPost(request, response);
            }
            httpServlet.destory(httpContext);
        }
        new ResponseHandler(assetManager).println(os, request, response);
        os.close();
        is.close();
    }

    private String readHttpHeaders(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int count = 0;
            while (count == 0) {
                count = is.available();
            }
            Log.i("ADS", "readHttpHeaders len " + count);
            byte[] buffer = new byte[count];
            int size = is.read(buffer, 0, count);
            String str = new String(buffer, 0, size);
            Log.i("ADS", "size " + size + "\nresult " + str);
            stringBuilder.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
