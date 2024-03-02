package com.example.spider.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.example.spider.dao.SpankbangDao;
import com.example.spider.dao.Vod;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 章杰
 * @Date 2024/2/29 13:45
 * @Version 1.0.0
 */
@Component
public class Spankbang {

    private static final String siteUrl = "https://spankbang.com";

    private static final String cateUrl = siteUrl + "/categories/";
    private static final String detailUrl = siteUrl;
    private static final String searchUrl = siteUrl + "/s/";

    private static final String CHROME = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", CHROME);
        return headers;
    }

    public List<Vod> searchContent(String key, String pg) throws Exception {
        List<Vod> list = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        String url = searchUrl.concat(URLEncoder.encode(key)).concat("/").concat(pg).concat("/").concat("?o=all");
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Document doc = Jsoup.parse(responseBody);
                // ... 继续处理文档
                for (Element element : doc.select("div.video-item")) {
                    String pic = element.select("img").attr("data-src");
                    String url1 = element.select("a").attr("href");
                    String name = element.select("a.n").text();
                    if (!name.isEmpty()){
                        Vod vod = new Vod(url1, name, pic);
                        list.add(vod);
                    }
                }
                return list;
            } else {
                // 处理响应不成功的情况
                // 可以抛出异常或返回适当的错误信息
                return list;
            }
        }
    }


    private static String checkVar(String var) {
        if (var.contains("'")) {
            return var.split("'")[1];
        }
        if (var.contains("\"")) {
            return var.split("\"")[1];
        }
        return "";
    }

    public static String getVar(String data, String param) {
        for (String var : data.split("var")) {
            if (var.contains(param)) {
                return checkVar(var);
            }
        }
        return "";
    }

    public SpankbangDao detailContent(String uid) throws Exception {

        OkHttpClient client = new OkHttpClient();
        String url = detailUrl.concat("/").concat(uid);
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .build();

        try (Response response = client.newCall(request).execute()) {
            SpankbangDao spankbangDao = new SpankbangDao();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Document doc = Jsoup.parse(responseBody);
                // ... 继续处理文档
                // 查找包含 JSON 数据的 <script> 标签
                Element scriptElement = doc.select("script[type=\"application/ld+json\"]").first();

                if (scriptElement != null) {
                    // 获取 <script> 标签中的 JSON 字符串
                    String jsonStr = scriptElement.html();

                    // 显式配置白名单以允许自动类型转换
                    ParserConfig config = new ParserConfig();
                    config.setAutoTypeSupport(true);

                    // 解析 JSON 字符串为 JSON 对象
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr, config);

                    // 在这里可以进一步处理 JSON 数据
                    // 例如，获取视频名称和描述：
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    String contentUrl = jsonObject.getString("contentUrl");
                    String thumbnailUrl = jsonObject.getString("thumbnailUrl");
                    String uploadDate = jsonObject.getString("uploadDate");
                    String duration = jsonObject.getString("duration");
                    String embedUrl = jsonObject.getString("embedUrl");

                    // 保存到SpankbangDao中
                    spankbangDao.setName(name);
                    spankbangDao.setDescription(description);
                    spankbangDao.setContentUrl(contentUrl);
                    spankbangDao.setThumbnailUrl(thumbnailUrl);
                    spankbangDao.setDuration(duration);
                    spankbangDao.setUploadDate(uploadDate);
                    spankbangDao.setEmbedUrl(embedUrl);
                    return spankbangDao;
                }
                return spankbangDao;
            } else {
                // 处理响应不成功的情况
                // 可以抛出异常或返回适当的错误信息
                return spankbangDao;
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        Spankbang spankbang = new Spankbang();
//
//        SpankbangDao spankbangDao = spankbang.detailContent("/6qe15/video/fc2+ppv+2699064");
//        System.out.println(spankbangDao.toString());
//
////        List<Vod> vodList = spankbang.searchContent("乳首", "1");
////        vodList.forEach(vod ->{
////            System.out.println(vodList.toString());
////        });
//    }
}
