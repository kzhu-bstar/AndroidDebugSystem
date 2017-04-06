package pig.dream.androiddebugsystem.http.request;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pig.dream.androiddebugsystem.http.HttpRequest;
import pig.dream.androiddebugsystem.http.HttpResponse;
import pig.dream.androiddebugsystem.http.HttpServlet;
import pig.dream.androiddebugsystem.http.RoutePath;

/**
 * Created by zhukun on 2017/4/5.
 */

@RoutePath("/ads/file")
public class AndroidFileHttpServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        LinkedHashMap<String, Object> fileMap = new LinkedHashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        File rootFile = null;
        Map<String, Object> map = request.getAttribute();
        Set<String> sets = map.keySet();
        for (String key : sets) {
            String value = (String) map.get(key);
            stringBuilder.append("Key: " + key + " Value: " + value);
            stringBuilder.append("<br>");
        }

        String path = (String) map.get("path");
        if (TextUtils.isEmpty(path)) {
            rootFile = httpContext.context.getFilesDir().getParentFile();
        } else {
            rootFile = new File(path);
        }

        scanDir(fileMap, rootFile);


        getListFile(fileMap, stringBuilder);


        response.setHtmlContent(stringBuilder.toString());
//        response.setHtmlFile("abc.html");
    }

    @Override
    public String toString() {
        return "AndroidFileHttpServlet";
    }

    private void scanDir(Map<String, Object> map, File rootFile) {
        if (rootFile == null) {
            return;
        }
        if (rootFile.isDirectory()) {
            File[] list = rootFile.listFiles();
            map.put(rootFile.getAbsolutePath(), list);
//            for (File file:list) {
//                scanDir(map, file);
//            }
        }
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
}
