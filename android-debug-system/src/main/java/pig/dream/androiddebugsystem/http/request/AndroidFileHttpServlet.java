package pig.dream.androiddebugsystem.http.request;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pig.dream.androiddebugsystem.http.HttpRequest;
import pig.dream.androiddebugsystem.http.HttpResponse;
import pig.dream.androiddebugsystem.http.HttpServlet;
import pig.dream.androiddebugsystem.http.RoutePath;
import pig.dream.androiddebugsystem.http.javebean.FileJavaBean;

/**
 * Created by zhukun on 2017/4/5.
 */

@RoutePath("/ads/file")
public class AndroidFileHttpServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> map = request.getAttribute();
        Set<String> sets = map.keySet();
        for (String key : sets) {
            String value = (String) map.get(key);
            stringBuilder.append("Key: " + key + " Value: " + value);
            stringBuilder.append("<br>");
        }

        File rootFile = httpContext.context.getFilesDir().getParentFile();
        String rootPath = rootFile.getAbsolutePath();
        String path = (String) map.get("path");
        if (TextUtils.isEmpty(path)) {
            path = rootPath;
        }

        List<FileJavaBean> list = getCurrentPath(rootPath, path);
        List<FileJavaBean> scanList = scanDir(path);

        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            String appName = packageInfo.applicationInfo.loadLabel(httpContext.context.getPackageManager()).toString();
            String versionName = packageInfo.versionName;
            httpContext.data.put("projectName", appName);
            httpContext.data.put("projectVersion", versionName);
        }
        Log.i("ADS", "list size " + list.size());

        httpContext.data.put("pathList", list);
        httpContext.data.put("scanList", scanList);

        httpContext.tplName = "file.btl";
    }

    @Override
    public String toString() {
        return "AndroidFileHttpServlet";
    }

    private List<FileJavaBean> scanDir(String path) {
        List<FileJavaBean> files = new ArrayList<>();
        if (TextUtils.isEmpty(path)) {
            return files;
        }
        File file = new File(path);
        if (!file.exists()) {
            return files;
        }
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File f:list) {
                FileJavaBean fileJavaBean = new FileJavaBean();
                fileJavaBean.setName(f.getName());
                fileJavaBean.setPath(f.getAbsolutePath());
                files.add(fileJavaBean);
            }
            return files;
        }
        return files;
    }

    private void getListFile(Map<String, Object> map, StringBuilder stringBuilder) {
        Set<String> sets = map.keySet();
        for (String key: sets) {
            stringBuilder.append("<br>");
            stringBuilder.append("<a href=\"/ads/file?path=");
            stringBuilder.append(key);
            stringBuilder.append("\">");
            stringBuilder.append(key);
            stringBuilder.append("</a>");

            File[] list = (File[]) map.get(key);
            for (File file: list) {
//                stringBuilder.append("<br>");
//                stringBuilder.append(file.getAbsolutePath());
                println(file, stringBuilder);
            }
        }
    }

    private void println(File file, StringBuilder stringBuilder) {
        if (file.isDirectory()) {
            stringBuilder.append("<br>");
            stringBuilder.append("<a href=\"/ads/file?path=");
            stringBuilder.append(file.getAbsolutePath());
            stringBuilder.append("\">");
            stringBuilder.append(file.getAbsolutePath());
            stringBuilder.append("</a>");
        } else {
            stringBuilder.append("<br>");
            stringBuilder.append(file.getAbsolutePath());
        }
    }

    private PackageInfo getPackageInfo() {
        PackageManager packageManager = httpContext.context.getPackageManager();
        PackageInfo pkg = null;
        try {
            pkg = packageManager.getPackageInfo(httpContext.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkg;
    }

    private List<FileJavaBean> getCurrentPath(String rootPath, String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith(rootPath)) {
            // error
            return null;
        }
        ArrayList<FileJavaBean> list = new ArrayList<>();
        File file = new File(path);
        while (!file.getAbsolutePath().equals(rootPath)) {
            FileJavaBean fileJavaBean = new FileJavaBean();
            fileJavaBean.setName(file.getName());
            fileJavaBean.setPath(file.getAbsolutePath());
            list.add(fileJavaBean);
            file = file.getParentFile();
        }

        FileJavaBean fileJavaBean = new FileJavaBean();
        fileJavaBean.setName("root");
        fileJavaBean.setPath(rootPath);
        list.add(fileJavaBean);

        int len = list.size();
        List<FileJavaBean> result = new ArrayList<>(len);
        for (int i = len -1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
