package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    String uploadVideoAly(MultipartFile file);

    void removeVideo(String videoId);
}
