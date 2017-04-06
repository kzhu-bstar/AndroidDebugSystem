package pig.dream.androiddebugsystem.http;

import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pig.dream.androiddebugsystem.utils.ClosableUtils;
import pig.dream.androiddebugsystem.utils.Utils;

/**
 * Created by zhukun on 2017/4/5.
 */

public class ResponseHandler {
    private AssetManager assetManager;

    public ResponseHandler(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void println(OutputStream os, HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] bytes = null;
        if (200 == httpResponse.getStatuCode()) {
            bytes = httpResponse.getHtmlContent();
            if (Utils.isEmpty(bytes)) {
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

    private byte[] getHtmlFromFile(String file) {
        Log.i("ADS", "getHtmlFromFile " + file);
        file = getAsssetFilePathFromUri(file);
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

    private String getAsssetFilePathFromUri(String uri) {
        if (uri != null && uri.startsWith("/")) {
            return uri.substring(1, uri.length());
        }
        return uri;
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
