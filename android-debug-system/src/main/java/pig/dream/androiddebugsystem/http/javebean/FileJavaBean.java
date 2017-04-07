package pig.dream.androiddebugsystem.http.javebean;

import java.io.Serializable;

/**
 * Created by zhukun on 2017/4/7.
 */

public class FileJavaBean implements Serializable {

    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
