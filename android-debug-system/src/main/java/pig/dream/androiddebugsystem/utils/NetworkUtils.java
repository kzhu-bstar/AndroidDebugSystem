package pig.dream.androiddebugsystem.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by zhukun on 2017/4/2.
 */

public class NetworkUtils {

    public static String getAddressLog(Context context, int port) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "Open http://" + formatedIpAddress + ":" + port + " in your browser";
    }
}

