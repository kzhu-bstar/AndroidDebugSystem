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
    private byte[] content = "".getBytes();

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
    public byte[] getHtmlContent() {
        return content;
    }

    @Override
    public void getLastModified() {

    }

    @Override
    public void setHtmlContent(byte[] content) {
        this.content = content;
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


    @Override
    public void setLastModified() {

    }
}
