package pig.dream.template.statement;

/**
 * Created by zhukun on 2017/4/9.
 */
public class StatementErrorException extends RuntimeException {

    public static final String Mismatch = "Statement(if or for) mismatch";

    public static final String LoseStatement = "Lose Statement ";
    public static final String NotSupportStatement = "Not support Statement( %s )";

    public StatementErrorException(String msg, Object... args) {
        super(String.format(msg, args));
    }
}
