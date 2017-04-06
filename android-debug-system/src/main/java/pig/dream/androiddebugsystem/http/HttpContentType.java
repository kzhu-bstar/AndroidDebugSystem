package pig.dream.androiddebugsystem.http;

import android.text.TextUtils;

import java.util.HashMap;

import static android.R.attr.x;

/**
 * Created by zhukun on 2017/4/6.
 */

public class HttpContentType {

    public final static HashMap<String, String> contentTypes = new HashMap<>();

    static {
        contentTypes.put(".png", "image/png");
        contentTypes.put(".gif", "image/gif");
        contentTypes.put(".jfif", "image/jpeg");
        contentTypes.put(".jpe", "image/jpeg");
        contentTypes.put(".jpeg", "image/jpeg");
        contentTypes.put(".jpg", "image/jpeg");
        contentTypes.put(".ico", "image/x-icon");
        contentTypes.put(".css", "text/css");
        contentTypes.put(".js", "application/javascript");
    }

    public static String ContentTypeByFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "text/html";
        }

        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String contentType = contentTypes.get(suffix);
            if (TextUtils.isEmpty(contentType)) {
                return "text/html";
            } else {
                return contentType;
            }
        }

        return "text/html";
    }
}
