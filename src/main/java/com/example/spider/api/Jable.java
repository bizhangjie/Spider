package com.example.spider.api;

import com.example.spider.dao.Vod;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Bean;
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
public class Jable {

    private static final String siteUrl = "https://jable.tv";
    private static final String cateUrl = siteUrl + "/categories/";
    private static final String detailUrl = siteUrl + "/videos/";
    private static final String searchUrl = siteUrl + "/search/";

    private static final String CHROME = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", CHROME);
        return headers;
    }

    public List<Vod> searchContent(String key, String pg) throws Exception {
        List<Vod> list = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = searchUrl.concat(URLEncoder.encode(key)).concat("/").concat("?mode=async&function=get_block&block_id=list_videos_videos_list_search_result")
                .concat("&q=" + URLEncoder.encode(key)).concat("&sort_by=").concat("&from=" + pg).concat("&_=" + System.currentTimeMillis());
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
                for (Element element : doc.select("div.video-img-box")) {
                    String pic = element.select("img").attr("data-src");
                    String url1 = element.select("a").attr("href");
                    String name = element.select("div.detail > h6").text();
                    String id = url1.split("/")[4];
                    list.add(new Vod(id, name, pic));
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

    public Vod detailContent(List<String> ids) throws Exception {

        OkHttpClient client = new OkHttpClient();
        String url = detailUrl.concat(ids.get(0)).concat("/");
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeaders()))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Document doc = Jsoup.parse(responseBody);
                // ... 继续处理文档
                String name = doc.select("meta[property=og:title]").attr("content");
                String pic = doc.select("meta[property=og:image]").attr("content");
                String year = doc.select("span.inactive-color").get(0).text();
                Vod vod = new Vod();
                vod.setVodId(ids.get(0));
                vod.setVodPic(pic);
                vod.setVodYear(year.replace("上市於 ", ""));
                vod.setVodName(name);
                vod.setVodPlayFrom("Jable");
                vod.setVodPlayUrl("播放$" + getVar(doc.html(), "hlsUrl"));
                return vod;
            } else {
                // 处理响应不成功的情况
                // 可以抛出异常或返回适当的错误信息
                return new Vod();
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        Jable jable = new Jable();
//        List<Vod> vodList = jable.searchContent("黑人", true);
//        vodList.forEach(vod -> {
//            System.out.println(vod.toString());
//        });
//        List<String> ids = new ArrayList<>();
//        ids.add("ngod-101");
//        String detailContent = jable.detailContent(ids);
//        System.out.println(detailContent.toString());
//    }
}
