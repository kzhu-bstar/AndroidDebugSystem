package pig.dream.androiddebugsystem.http;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhukun on 2017/4/7.
 */

public class BeetlHelper {

    public static String render(String content, HashMap<String, Object> map) throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate(content);
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                t.binding(entry.getKey(), entry.getValue());
            }
        }
        return t.render();
    }
}
