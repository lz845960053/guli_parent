package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description="front讲师")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;


    //1 分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page")long page,@PathVariable("limit")long limit){
        //获取分页对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        //返回所有数据
        return R.ok().data(map);
    }

    //2 根据讲师id查询讲师所讲课程列表
    @GetMapping("getTeacherCourse/{teacherId}")
    public R getTeacherCourse(@PathVariable("teacherId") String teacherId){
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        List<EduCourse> courseList = courseService.courseService(teacherId);
        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}
