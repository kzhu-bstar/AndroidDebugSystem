package pig.dream.androiddebugsystem.http;

import java.io.IOException;
import java.util.HashMap;

import pig.dream.template.Template;

/**
 * Created by zhukun on 2017/4/10.
 */

public class AtlHelper {

    public static String render(String content, HashMap<String, Object> map) throws IOException {

        Template template = Template.createTemplate(null, content);
        template.binding(map);
        return template.render();
//        return "";
    }
}
