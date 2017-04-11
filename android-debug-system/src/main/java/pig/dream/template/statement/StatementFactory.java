package pig.dream.template.statement;

import static pig.dream.template.statement.StatementErrorException.NotSupportStatement;

/**
 * Created by zhukun on 2017/4/10.
 */

public class StatementFactory {


    public static Statement createStatement(int type, String result) {
        if (type == Statement.TYPE.IF) {
            return new IfStatement(result);
        } else {
            throw new StatementErrorException(NotSupportStatement);
        }
    }

    public static boolean checkStatementType(Statement statement, int type) {
        if (statement == null) {
            return false;
        }
        return statement.type() == type;
    }
}
