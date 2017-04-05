package pig.dream.androiddebugsystem.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhukun on 2017/4/1.
 */

public class ClosableUtils {

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return ;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
