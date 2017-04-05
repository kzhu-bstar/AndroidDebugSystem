package pig.dream.androiddebugsystem.http;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhukun on 2017/4/5.
 */

public interface HttpRequest {

    public static final String GET = "GET";
    public static final String POST = "POST";

    public Map<String, Object> getAttribute();

    public String getMethod();

    public String getUri();

    public String getProtocol();

    public Map<String, Object> getHeaders();

    public Set<String> getHeaderNames();

    public Object getHeader(String key);
}
