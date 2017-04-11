package pig.dream.template.statement;

import java.util.Map;

/**
 * Created by zhukun on 2017/4/10.
 */

public class IfStatement extends Statement {

    protected IfStatement(String result) {
        super(result);
    }

    @Override
    protected String exec(Map<String, Object> data) {
        String[] split = variable.split("\\ ");

        Object object = data.get(split[1]);
        boolean flag = (boolean) object;
        if (flag) {
            return out;
        }
        return "";
    }

    @Override
    protected int type() {
        return TYPE.IF;
    }
}
