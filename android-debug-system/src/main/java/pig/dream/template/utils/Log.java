package pig.dream.template.utils;

import java.util.Date;

/**
 * Created by zhukun on 2017/4/7.
 */
public class Log {

    private final static boolean DBG = true;
    private final static String formatStr = "%s ";

    public static void i(String format, Object... args) {
        if (!DBG) {
            return;
        }
        String msg = String.format(format, args);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formatStr, new Date()));
        sb.append(msg);
        System.out.println(sb.toString());
    }
}
