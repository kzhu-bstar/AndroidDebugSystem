package pig.dream.androiddebugsystem.http;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pig.dream.androiddebugsystem.utils.Utils;

/**
 * Created by zhukun on 2017/4/5.
 */

public class ResponseHandler {

    public ResponseHandler() {
    }

    public void start(Context context, HttpRequest request, OutputStream os) throws IOException {
        String uri = request.getUri();
        HttpResponse response = new HttpServletResponse();
        IHttpServlet httpServlet = HttpRoute.getInstance().getHttpServletByPath(uri);
        if (httpServlet == null) {
            Log.i("ADS", "----------" + uri);
            response.setStatuCode(HttpCode.HTTP_NOT_FOUND);
        } else {
            HttpContext httpContext = new HttpContext();
            httpContext.context = context;
            httpServlet.init(httpContext);
            httpServlet.start(request, response);
            httpServlet.destory(httpContext);
        }
        out(os, request, response);
    }


    public void out(OutputStream os, HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] bytes = null;
        if (200 == httpResponse.getStatuCode()) {
            bytes = httpResponse.getHtmlContent();
            if (Utils.isEmpty(bytes)) {
                httpResponse.setStatuCode(HttpCode.HTTP_NOT_FOUND);
            }
        }

        StringBuilder sb = new StringBuilder();
        //状态行
        sb.append(httpRequest.getProtocol() + " " + httpResponse.getStatuCode() + " " + httpResponse.getStatuCodeStr() + "\r\n");
        //响应头
        sb.append("Server: " + HttpResponse.SERVER_NAME + "\r\n");
        sb.append("Content-Type: " + httpResponse.getContentType() + "\r\n");
        sb.append("Date: " + Utils.GetCurrentDate() + "\r\n");
        sb.append("Last-Modified: " + Utils.GetCurrentDate() + "\r\n");

        if (200 == httpResponse.getStatuCode()) {
            sb.append("Content-Length: " + bytes.length + "\r\n");

            //响应内容
            sb.append("\r\n");
//            sb.append(html);
        }


        BufferedOutputStream bos = new BufferedOutputStream(os);
        try {
            bos.write(sb.toString().getBytes());
            if (!Utils.isEmpty(bytes)) {
                bos.write(bytes);
            }
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
