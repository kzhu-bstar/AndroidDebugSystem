package pig.dream.androiddebugsystem.http;

import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import pig.dream.androiddebugsystem.utils.ClosableUtils;

/**
 * Created by zhukun on 2017/4/5.
 */

public class ResponseHandler {
    private AssetManager assetManager;

    public ResponseHandler(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void println(OutputStream os, HttpRequest httpRequest, HttpResponse httpResponse) {
        String html = null;
        byte[] bytes = null;
        if (200 == httpResponse.getStatuCode()) {
            html = httpResponse.getHtmlContent();
            if (TextUtils.isEmpty(html)) {
                bytes = getHtmlFromFile(httpResponse.getHtmlFile());
            }
            if (bytes == null || bytes.length == 0) {
                httpResponse.setStatuCode(HttpCode.HTTP_NOT_FOUND);
            } else {
//                html = new String(bytes);
            }
        }

        StringBuilder sb = new StringBuilder();
        //状态行
        sb.append(httpRequest.getProtocol() + " " + httpResponse.getStatuCode() + " " + httpResponse.getStatuCodeStr() + "\r\n");
        //响应头
        sb.append("Server: " + HttpResponse.SERVER_NAME + "\r\n");
        sb.append("Content-Type: " + httpResponse.getContentType() + "\r\n");
        sb.append("Date: " + new Date() + "\r\n");
        if (200 == httpResponse.getStatuCode() && !TextUtils.isEmpty(html)) {
                sb.append("Content-Length: " + html.getBytes().length + "\r\n");

                //响应内容
                sb.append("\r\n");
                sb.append(html);
        }


        BufferedOutputStream bos = new BufferedOutputStream(os);
//        PrintWriter printWriter = new PrintWriter(os);
        try {
            bos.write(sb.toString().getBytes());
            if (bytes != null) {
                bos.write(bytes);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getHtmlFromFile(String file) {
        Log.i("ADS", "getHtmlFromFile " + file);
        if ("/i/eg_tulip.jpg".equals(file)) {
            file = "i/eg_tulip.jpg";
        }
        InputStream is = null;
        try {
            is = assetManager.open(file);
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

    public void reseponse(int code) {

    }

    public String detectMimeType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else {
            return "application/octet-stream";
        }
    }


}
