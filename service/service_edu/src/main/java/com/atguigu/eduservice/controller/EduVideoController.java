package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id")String id){
        //先删除视频
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeVideo(videoSourceId);
            // R result = vodClient.removeAlyVideo(videoSourceId);
        }
        //在删除表中数据
        videoService.removeById(id);
        return R.ok();
    }
    //根据id查询小节
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId){
        EduVideo videoInfo = videoService.getById(videoId);
        return R.ok().data("videoInfo",videoInfo);
    }

    //更新小节
    //@PostMapping("updateVideo")
    @PutMapping("updateVideo")
    public R updateVido(@RequestBody EduVideo eduVideo){
        boolean b = videoService.updateById(eduVideo);
        return R.ok();
    }

}

