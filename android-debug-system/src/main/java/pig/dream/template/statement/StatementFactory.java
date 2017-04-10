package pig.dream.template.statement;

/**
 * Created by zhukun on 2017/4/10.
 */

public class StatementFactory {

    public static final int TYPE_IF = 1;
    public static final int TYPE_FOR = 2;

    public static Statement createStatement(int type, String result) {
        if (type == TYPE_IF) {
            return new IfStatement(result);
        }

        return null;
    }

}
