package pig.dream.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pig.dream.template.statement.StatementDispatcher;

/**
 * Created by zhukun on 2017/4/7.
 */

public class Template {

    private Configuration cf;
    private String content;
    private Map<String, Object> data;


    public static Template createTemplate(Configuration cf, String content) {
        Template template = new Template();
        template.cf = cf;
        template.content = content;
        return template;
    }

    private Template() {
        data = new HashMap<>();
    }

    public void binding(Map<String, Object> map) {
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                binding(entry.getKey(), entry.getValue());
            }
        }
    }

    public void binding(String key, Object value) {
        data.put(key, value);
    }

    public String render() {
        Functions functions = new Functions();
        StatementDispatcher dispatcher = new StatementDispatcher();
        Parameter parameter = new Parameter();

//        String r = functions.replaceFunction(content, data);
        dispatcher.pretreat(content);
        String r = dispatcher.dispatcher(content, data);
        return parameter.exec(r, data);
    }
}
