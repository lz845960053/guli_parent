package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Api(description="课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        if(StringUtils.isEmpty(id)){
            return R.error().message("添加课程失败");
        }else{
            return R.ok().data("courseId",id);
        }

    }
    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfoById/{id}")
    @ApiOperation(value = "查询课程")
    public R getCourseInfoById(@PathVariable("id") String id){
       CourseInfoVo courseInfoVo= eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoVo);
    }
    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        int record =  eduCourseService.updateCourseInfo(courseInfoVo);
        if(record!=1){
            return R.error().message("修改课程失败！");
        }
        return  R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/id")
    public R getPublishCourseInfo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

}

