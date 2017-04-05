package pig.dream.androiddebugsystem.base;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pig.dream.androiddebugsystem.utils.ClosableUtils;

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
            while (isRunning) {
                Socket socket = serverSocket.accept();

                Log.i("ADS","------------------");
                requestHandler.handle(socket);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
