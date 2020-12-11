package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.Future;

public interface VideoService {
    String uploadVideoAly(MultipartFile file);

    void removeVideo(String videoId);

    void removeMoreAlyVideo(List<String> videoIdList);

    void excutVoidTask(int i) throws InterruptedException;

    Future<String> excuteValueTask(int i) throws InterruptedException;

}
