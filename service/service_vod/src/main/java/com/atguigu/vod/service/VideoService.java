package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideoAly(MultipartFile file);

    void removeVideo(String videoId);

    void removeMoreAlyVideo(List<String> videoIdList);
}
