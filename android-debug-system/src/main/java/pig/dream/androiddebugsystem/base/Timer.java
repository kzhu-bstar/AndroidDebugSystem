package pig.dream.androiddebugsystem.base;

import android.util.Log;

/**
 * 用于打印方法执行的时间
 *
 * Created by zhukun on 2017/3/15.
 */

public class Timer {

    public static final String TAG = "Timer";
    public static final boolean DBG = true;

    private static long startTime = 0;

    public static void init() {
        startTime = System.currentTimeMillis();
    }

    public static void out(String msg) {
        if (!DBG) {
            return ;
        }
        long time = System.currentTimeMillis();
        Log.i(TAG, msg + (time - startTime));
        startTime = time;
    }
}
