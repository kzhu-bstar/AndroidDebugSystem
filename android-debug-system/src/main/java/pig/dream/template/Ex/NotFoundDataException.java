package pig.dream.template.Ex;

/**
 * Created by zhukun on 2017/4/9.
 */
public class NotFoundDataException extends Exception {

    public NotFoundDataException(String data) {
        super("not found data " + data);
    }

}
