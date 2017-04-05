package pig.dream.androiddebugsystem.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhukun on 2017/4/1.
 */

public class CoreServer implements Runnable {

    private static final String TAG = "";

    private RequestHandler requestHandler;
    private boolean isRunning;
    private ServerSocket serverSocket;
    private int port;

    public CoreServer(Context context, int port) {
        HttpRoute.getInstance().initialize(context);
        HttpRoute.getInstance().print();
        requestHandler = new RequestHandler(context);
        this.port = port;
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
        if (null != serverSocket) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverSocket = null;
        }
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Log.e("ADS", "Server Socket init error");
        }
        try {
            while (isRunning) {
                Socket socket  = serverSocket.accept();
                requestHandler.handle(socket);
                socket.close();
            }
        } catch (IOException e) {
            Log.e("ADS", "Server Socket close...");
        }

    }
}
