package pig.dream.template;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhukun on 2017/4/7.
 */

public class Template {

    private Configuration cf;
    private String content;

    public static Template createTemplate(Configuration cf, String content) {
        Template template = new Template();
        template.cf = cf;
        template.content = content;
        return template;
    }

    private Template() {

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

    }

    public String render() {
        return null;
    }

    /**
     * 替换模板变量
     *
     * @param template
     * @param data
     * @return
     */
    public static String replaceArgs(String template, Map<String, String> data) {
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String name = matcher.group(1);// 键名
                String value = (String) data.get(name);// 键值
                if (value == null) {
                    value = "";
                } else {
                    value = value.replaceAll("\\$", "\\\\\\$");
                }
                matcher.appendReplacement(sb, value);
            }
            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
            matcher.appendTail(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString() + enter;   //加一个空行（结束行）
    }

    // 换行符
    private static String enter = System.getProperty("line.separator");
}
