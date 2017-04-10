package pig.dream.template.statement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pig.dream.template.utils.Log;
import pig.dream.template.utils.Utils;

/**
 * Created by zhukun on 2017/4/9.
 */
public class ForStatement {

    public String group;
    public int start;
    public int end;

    private String parameterStr;


    public String exec(String result, Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        String[] split = group.split("\\ ");
        int len = split.length;
        Log.i("ForFunction split length " + len);

        String out = result.substring(start, end);

        Object object = data.get(split[3]);
        Log.i("ForFunction Obj class Type " + object.getClass());
        if (object instanceof List) {
            List list = (List) object;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String r = deploy(out, split[1], list.get(i));
                Log.i("ForFunction exec r " + r);
                sb.append(r);
            }
        }

//        Parameter parameter = new Parameter();
//        parameter.exec(out, data);

        Log.i("ForFunction out[]" + sb.toString());
        return sb.toString();
    }


    public String deploy(String content, String parameterStr, Object object) {
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String name = matcher.group(1).trim();// 键名

                String[] split = name.split("\\.");
                if (parameterStr.equals(split[0])) {
                    String value = null;
                    if (object == null) {
                        value = "";
                    } else if (split.length == 1) {
                        Log.i("Class Type " + object.getClass());
                        value = String.valueOf(object);
                    } else {
                        Log.i("Class Type " + object.getClass());
                        value = getObjectValue(object, split, 1);
                    }
                    Log.i("find name: " + name + " value: " + value);
                    if (!Utils.isEmpty(value)) {
                        value = value.replaceAll("\\$", "\\\\\\$");
                        matcher.appendReplacement(sb, value);
                    }
                }

            }
            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
            matcher.appendTail(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private String parseArgs(String src, Map<String, Object> data) {
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
        if (Utils.isEmpty(value)) {
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
            if (index == argv -1) {
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
}
