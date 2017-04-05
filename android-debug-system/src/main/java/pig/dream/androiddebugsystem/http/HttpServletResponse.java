package pig.dream.androiddebugsystem.http;

import java.nio.channels.SelectionKey;

/**
 * Created by zhukun on 2017/4/5.
 */

public class HttpServletResponse implements HttpResponse {

    private SelectionKey key;
    //内容类型  defalut 为text/html
    private String contentType = "text/html";
    //响应码  defalut 为200
    private HttpCode.Code statuCode = HttpCode.HTTP_OK;
    private String htmlFile = "";
    private String content = "";

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public int getStatuCode() {
        return statuCode.httpStatusCode;
    }

    @Override
    public String getStatuCodeStr() {
        return statuCode.httpStatusCodeStr;
    }

    @Override
    public String getHtmlContent() {
        return content;
    }

    @Override
    public String getHtmlFile() {
        return htmlFile;
    }

    @Override
    public void setHtmlContent(String html) {
        this.content = html;
    }

    @Override
    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    @Override
    public SelectionKey getKey() {
        return key;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setStatuCode(HttpCode.Code statuCode) {
        this.statuCode = statuCode;
    }
}
