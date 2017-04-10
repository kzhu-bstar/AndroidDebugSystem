package pig.dream.template.statement;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Matcher;

import pig.dream.template.utils.Log;
import pig.dream.template.utils.Utils;

/**
 * 基本语句类
 *
 * Created by zhukun on 2017/4/10.
 */

public abstract class Statement {

    /** 需要处理的字符串 */
    private String result;

    /** 第一个括号中的字符串数据 */
    private String variable;

    /** 带输出的字符串数据 */
    private String out;

    private int lastIndex;
    private int start;
    private int end;

    private String lastStr = "";

    protected Statement(String result) {
        this.result = result;
    }

    protected abstract String exec(Map<String, Object> data);

    public void start(String variable, Matcher matcher, int lastIndex) {
        this.variable = variable;
        this.lastIndex = lastIndex;
        this.start = matcher.end();

        lastStr = result.substring(lastIndex, matcher.start());
    }

    public void end(StringBuilder sb, Map<String, Object> data, Matcher matcher) {
        this.end = matcher.start();
        this.out = result.substring(start, end);

        sb.append(lastStr);
        sb.append(exec(data));
    }

    public void appenTail(StringBuilder sb, String r, int lastIndex) {
        sb.append(r.substring(lastIndex, r.length()));
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
