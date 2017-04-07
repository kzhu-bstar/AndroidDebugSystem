package pig.dream.androiddebugsystem.http;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zhukun on 2017/4/7.
 */

public class FreemarkerHelper {

    public static String render(String content, HashMap<String, Object> map) throws IOException {
//        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
//        StringTemplateLoader stringLoader = new StringTemplateLoader();
//        stringLoader.putTemplate("myTemplate",content);
//
//        cfg.setTemplateLoader(stringLoader);
//
//        Template template = cfg.getTemplate("myTemplate","utf-8");
//
//        StringWriter writer = new StringWriter();
//        try {
//            template.process(map, writer);
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//        return writer.toString();
        return "";
    }
}
