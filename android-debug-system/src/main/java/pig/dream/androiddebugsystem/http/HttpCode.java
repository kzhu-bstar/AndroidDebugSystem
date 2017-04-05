package pig.dream.androiddebugsystem.http;

/**
 * Created by zhukun on 2017/4/5.
 */

public class HttpCode {
    public static final Code HTTP_OK = new Code(200, "OK");
    public static final Code HTTP_NOT_FOUND = new Code(404, "Not Found");

    public static class Code {
        public int httpStatusCode;
        public String httpStatusCodeStr;

        public Code(int code, String codeStr) {
            this.httpStatusCode = code;
            this.httpStatusCodeStr = codeStr;
        }
    }
}
