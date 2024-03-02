package com.example.spider.dao;

import java.io.Serializable;

/**
 * @Author 章杰
 * @Date 2024/3/2 15:47
 * @Version 1.0.0
 */
public class SpankbangDao implements Serializable {
    private String name;
    private String description;
    private String contentUrl;
    private String thumbnailUrl;
    private String uploadDate;
    private String duration;
    private String embedUrl;

    // 构造方法
    public SpankbangDao() {
    }

    // 获取视频名称
    public String getName() {
        return name;
    }

    // 设置视频名称
    public void setName(String name) {
        this.name = name;
    }

    // 获取视频描述
    public String getDescription() {
        return description;
    }

    // 设置视频描述
    public void setDescription(String description) {
        this.description = description;
    }

    // 获取视频内容URL
    public String getContentUrl() {
        return contentUrl;
    }

    // 设置视频内容URL
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    // 获取视频缩略图URL
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    // 设置视频缩略图URL
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    // 获取视频上传日期
    public String getUploadDate() {
        return uploadDate;
    }

    // 设置视频上传日期
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    // 获取视频时长
    public String getDuration() {
        return duration;
    }

    // 设置视频时长
    public void setDuration(String duration) {
        this.duration = duration;
    }

    // 获取视频嵌入URL
    public String getEmbedUrl() {
        return embedUrl;
    }

    // 设置视频嵌入URL
    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    @Override
    public String toString() {
        return "SpankbangDao{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", duration='" + duration + '\'' +
                ", embedUrl='" + embedUrl + '\'' +
                '}';
    }
}