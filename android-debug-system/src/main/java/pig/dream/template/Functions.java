package pig.dream.template;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pig.dream.template.statement.ForStatement;
import pig.dream.template.utils.Log;

/**
 * Created by zhukun on 2017/4/9.
 */
public class Functions {


    private final static Map<String, String> funcKeyWords = new HashMap<>();

    static {
        funcKeyWords.put("if", "aaa");
        funcKeyWords.put("endif", "bbb");
        funcKeyWords.put("for", "bbb");
        funcKeyWords.put("endfor", "bbb");
    }

    private ForStatement forStatement;
    private String temp;
    private int lastIndex = 0;

    public String replaceFunction(String result, Map<String, Object> data) {
        StringBuffer sb = new StringBuffer();
        try {
            Pattern pattern = Pattern.compile("<%(.+?)%>");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                String name = matcher.group(1).trim();// 键名
                Log.i("replaceFunction: " + name);

                if (name.startsWith("for")) {
                    forStatement = new ForStatement();
                    forStatement.group = name;
                    forStatement.start = matcher.end();
                    temp = result.substring(lastIndex, matcher.start());
//                    matcher.appendReplacement(sb, "");
                } else if (name.startsWith("endfor")) {
                    forStatement.end = matcher.start();
                    String r = forStatement.exec(result, data);
//                    r = r.replaceAll("\\$", "\\\\\\$");
//                    matcher.appendReplacement(sb, r);
                    sb.append(temp);
                    sb.append(r);
                    lastIndex = matcher.end();
                } else {
//                    matcher.appendReplacement(sb, "");
                }

//                Log.i("after: string: " + sb.toString());
            }
            // 最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
//            matcher.appendTail(sb);
            sb.append(result.substring(lastIndex, result.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String out = sb.toString();
        System.out.println("Functions result: " + out);
        return sb.toString();
    }
}
