package pig.dream.androiddebugsystem.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhukun on 2017/4/1.
 */

public class RequestHandler {
    Context context;

    public RequestHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    public void handle(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        String httpHeaders = readHttpHeaders(is);
        HttpRequest request = new HttpServletRequest(httpHeaders);
        Log.i("ADS", "HttpServletRequest uri " + request.getUri() + " Method " + request.getMethod());

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.start(context, request, os);
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
