package pig.dream.template;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pig.dream.template.utils.Log;
import pig.dream.template.utils.Utils;

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
        String r = functions.replaceFunction(content, data);
        return replaceArgs(r);
    }

    private String replaceArgs(String r) {
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher matcher = pattern.matcher(r);
            while (matcher.find()) {
                String name = matcher.group(1).trim();// 键名
                String[] split = name.split("\\.");
                Log.i("Split --- " + split.length);
                int splitLen = split.length;
                Object obj = data.get(split[0]);// 键值

                String value = parseArgs(name);
                Log.i("after: value: " + value);
                matcher.appendReplacement(sb, value);
//                Log.i("after: string: " + sb.toString());
            }
            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
            matcher.appendTail(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String parseArgs(String src) {
        String[] split = src.split("\\.");
        Log.i("Split --- " + split.length);
        int splitLen = split.length;
        Object obj = data.get(split[0]);// 键值

        String value = null;
        if (obj == null) {
            value = "";
        } else if (splitLen == 1) {
            value = String.valueOf(obj);
        } else {
            Log.i("Class Type " + obj.getClass());
            value = getObjectValue(obj, split, 1);
        }
        Log.i("find name: " + src + " value: " + value);
        if (!Utils.isEmpty(value)) {
            value = value.replaceAll("\\$", "\\\\\\$");
        }
        return value;
    }

    private String getObjectValue(Object object, String[] args, int index) {
        String result = "";
        int argv = args.length;
        try {
            String fieldName = args[index];
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object getValue = field.get(object);
            if (index == argv - 1) {
                result = String.valueOf(getValue);
            } else {
                result = getObjectValue(getValue, args, index + 1);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 替换模板变量
     *
     * @param template
     * @param data
     * @return
     */
    public static String replaceArgs(String template, Map<String, String> data) {
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。(\$\{(.+?)\})|(<%(.+?)%>)
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
//            Pattern pattern = Pattern.compile("(\\$\\{(.+?)\\})|(\\<\\%(.+?)\\%\\>)");
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String name = matcher.group(1);// 键名
                String value = null;
                if (name == null || name.length() == 0) {
                    value = null;
                } else {
                    value = data.get(name.trim());// 键值
                }
                Log.i("find name: " + name + " value: " + value);
                if (value == null) {
                    value = "";
                } else {
                    value = value.replaceAll("\\$", "\\\\\\$");
                }
                Log.i("after: value: " + value);
                matcher.appendReplacement(sb, value);
                Log.i("after: string: " + sb.toString());
            }
            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
            matcher.appendTail(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString() + enter;   //加一个空行（结束行）
    }

    private static String enter = System.getProperty("line.separator");
}
