package pig.dream.androiddebugsystem.http;

import java.nio.channels.SelectionKey;

/**
 * Created by zhukun on 2017/4/5.
 */

public interface HttpResponse {

    public static final String SERVER_NAME = "ADS";

    public String getContentType();

    public int getStatuCode();

    public String getStatuCodeStr();

    public String getHtmlContent();

    public String getHtmlFile();

    public void setHtmlContent(String html);

    public void setHtmlFile(String htmlFile);

    public SelectionKey getKey();

    public void setContentType(String contentType);

    public void setStatuCode(HttpCode.Code statuCode);
}
