package pig.dream.androiddebugsystem;

import android.content.Context;
import android.util.Log;

import pig.dream.androiddebugsystem.http.CoreServer;
import pig.dream.androiddebugsystem.utils.NetworkUtils;

/**
 * Android Debug System Core
 *
 * @author zhukun
 * @version 1.0
 */

public class ADS {

    private static final String TAG = "ADS";
    private static final int DEFAULT_PORT = 8080;
    private static CoreServer clientServer;
    private static String addressLog = "not available";

    /**
     * android debug system 初始化
     *
     */
    public static void initialize(Context context) {
        int portNumber = DEFAULT_PORT;
        clientServer = new CoreServer(context, portNumber);
        clientServer.start();
        addressLog = NetworkUtils.getAddressLog(context, portNumber);
        Log.i(TAG, addressLog);
    }

    public static void shutDown() {
        if (clientServer != null) {
            clientServer.stop();
            clientServer = null;
        }
    }
}
