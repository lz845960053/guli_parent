package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VideoService videoService;

    //上传视频
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        //返回上传视频的ID
        String videoId = videoService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    //删除视频
    @DeleteMapping("{videoId}")
    public R removeVideo( @PathVariable("videoId") String videoId){
        videoService.removeVideo(videoId);
        return R.ok();
    }


}
