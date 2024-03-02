package com.example.spider.controller;

import com.example.spider.api.Jable;
import com.example.spider.api.Spankbang;
import com.example.spider.dao.SpankbangDao;
import com.example.spider.dao.Vod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 章杰
 * @Date 2024/2/29 15:23
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/spankbang")
public class SpankbangController {

    @Autowired
    private Spankbang spankbangAPI;

    private static final String CacheData = "spankbang";


    @GetMapping("/search/{wd}/{pg}") // Mapping for /search/{wd}/{pg}
    @Cacheable(value = CacheData + "_searchCache", key = "{#wd, #pg}")
    public Object search(@PathVariable String wd, @PathVariable String pg) throws Exception {
        // Assuming spankbangAPI.searchContent returns a List<Vod>
        List<Vod> vodList = spankbangAPI.searchContent(wd, pg);
        return vodList;
    }

    @GetMapping("/play")
    @Cacheable(value = CacheData + "_playCache", key = "#uid")
    public SpankbangDao play(@RequestParam String uid) throws Exception {
        SpankbangDao detailContent = spankbangAPI.detailContent(uid);
//        System.out.println(detailContent.toString());
        return detailContent;
    }
}
