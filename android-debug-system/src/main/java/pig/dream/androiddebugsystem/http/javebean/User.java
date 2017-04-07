package pig.dream.androiddebugsystem.http.javebean;

import java.io.Serializable;

/**
 * Created by zhukun on 2017/4/7.
 */

public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
