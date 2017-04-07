package pig.dream.androiddebugsystem.http;

import android.content.Context;

import java.util.HashMap;

/**
 * http context 上下文信息
 *
 * Created by zhukun on 2017/4/6.
 */

public class HttpContext {

    public Context context;
    public HashMap<String, Object> data = new HashMap<>();
    public String tplName;
}
