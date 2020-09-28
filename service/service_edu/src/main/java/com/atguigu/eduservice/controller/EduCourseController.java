package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "查询课程详情")
    public R getCourseInfoById(@PathVariable("id") String id){
       CourseInfoVo courseInfoVo= eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoVo);
    }
    //修改课程信息
    @ApiOperation(value = "更新课程详情")
    @PutMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        int record =  eduCourseService.updateCourseInfo(courseInfoVo);
        if(record!=1){
            return R.error().message("修改课程失败！");
        }
        return  R.ok();
    }

    //根据课程id查询课程确认信息
    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(courseId);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    //课程最终发布
    @ApiOperation(value = "课程最终发布")
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId")String courseId){
        boolean result = eduCourseService.publishCourse(courseId);
        return R.ok();
    }
    //获取课程列表
    @ApiOperation(value = "获取课程列表")
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }
    //获取课程列表
    @ApiOperation(value = "分页获取课程列表")
    @PostMapping("getCoursePageList/{page}/{limit}")
    public R getCoursePageList(@RequestBody CourseQuery courseQuery,@PathVariable("page") long page,@PathVariable("limit") long limit){
        //创建page对象
        Page<EduCourse> eduCoursePage = new Page<EduCourse>(page, limit);

        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        if(!StringUtils.isEmpty(title)){
            eduCourseQueryWrapper.like("title",title);
        }
        String status = courseQuery.getStatus();
        if(!StringUtils.isEmpty(status)){
            eduCourseQueryWrapper.like("status",status);
        }
        eduCourseQueryWrapper.orderByDesc("gmt_create");
        //查询数据封装在eduCoursePage中
        eduCourseService.page(eduCoursePage, eduCourseQueryWrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return  R.ok().data("total",total).data("rows",records);
    }
    //根据id删除课程
    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable("courseId")String courseId){
        eduCourseService.deleteCourseById(courseId);
        return R.ok();
    }

}

