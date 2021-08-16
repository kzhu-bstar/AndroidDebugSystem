package pig.dream.androiddebugsystem.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;
import pig.dream.androiddebugsystem.http.request.StaticFileHttpSevlet;

/**
 * Created by zhukun on 2017/4/5.
 */

public class HttpRoute {

    private HashMap<String, IHttpServlet> map = new HashMap<>();
    private StaticFileHttpSevlet staticFileHttpSevlet = new StaticFileHttpSevlet();

    private final static class SINGLEHOLDER {
        public static final HttpRoute INSTANCE = new HttpRoute();
    }

    public static HttpRoute getInstance() {
        return SINGLEHOLDER.INSTANCE;
    }

    public void initialize(Context ctx) {
        List<String> classNameList = getClassName(ctx, "pig.dream.androiddebugsystem.http.request");
        for (int i = 0; i < classNameList.size(); i++) {
            Log.e("hjo", "获取到的类名：" + classNameList.get(i));
            try {
                Class c = Class.forName(classNameList.get(i));
                if (c.isAnnotationPresent(RoutePath.class)) {
                    // 得到activity这个类的ContentView注解
                    RoutePath contentView = (RoutePath) c.getAnnotation(RoutePath.class);
                    // 得到注解的值
                    String path = contentView.value();
                    Constructor cons = c.getDeclaredConstructor();
                    Object object = cons.newInstance();
                    if (object instanceof IHttpServlet) {
                        map.put(path, (IHttpServlet) object);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public IHttpServlet getHttpServletByPath(String path) {
        IHttpServlet httpSevlet = map.get(path);
        if (httpSevlet == null) {
            httpSevlet = staticFileHttpSevlet;
        }
        return httpSevlet;
    }

    private List<String> getClassName(Context ctx, String packageName) {
        List<String> classNameList = new ArrayList<String>();
        try {
            Log.i("ADS", "PackageResourcePath: " + ctx.getPackageResourcePath());
            DexFile df = new DexFile(ctx.getPackageResourcePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();
                Log.i("ADS", "DexFile entries className: " + className);

                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    public void print() {
        Set<String> set = map.keySet();
        for (String str:set) {
            Log.i("ADS", "key " + str + " value " + map.get(str).toString());
        }
    }
}
