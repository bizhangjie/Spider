package com.example.spider.controller;

import com.example.spider.api.Jable;
import com.example.spider.dao.Vod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 章杰
 * @Date 2024/2/29 15:23
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/jable")
public class JableController {

    @Autowired
    private Jable jableApi;

    private static final String CacheData = "jable";


    @GetMapping("/search/{wd}/{pg}") // Mapping for /search/{wd}/{pg}
    @Cacheable(value = CacheData + "_searchCache", key = "{#wd, #pg}")
    public Object search(@PathVariable String wd, @PathVariable String pg) throws Exception {
        // Assuming jableApi.searchContent returns a List<Vod>
        List<Vod> vodList = jableApi.searchContent(wd, pg);
        return vodList;
    }

    @GetMapping("/play/{uid}")
    @Cacheable(value = CacheData + "_playCache", key = "#uid")
    public Vod play(@PathVariable String uid) throws Exception {
        List<String> ids = new ArrayList<>();
        ids.add(uid);
        Vod detailContent = jableApi.detailContent(ids);
//        System.out.println(detailContent.toString());
        return detailContent;
    }
}
