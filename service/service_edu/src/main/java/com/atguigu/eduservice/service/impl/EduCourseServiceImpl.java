package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        String courseId = eduCourse.getId();
        //课程描述表添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(courseId);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(!save){
            throw new GuliException(20001,"添加课程描述信息失败");
        }
        return courseId;
    }
    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfoById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new GuliException(20001,"数据不存在");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //查询课程描述信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public int updateCourseInfo(CourseInfoVo courseInfoVo) {
        int record;
        //更新课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        record = baseMapper.updateById(eduCourse);
        if(record == 0) {
            throw new GuliException(20001,"修改课程信息失败");
        }
        //更新课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
        return record;
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
        //调用mapper
        CoursePublishVo publishCourseInfo  = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }
    @Override
    public boolean publishCourse(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        int count = baseMapper.updateById(eduCourse);
        return  count > 0 ;

    }

    @Override
    public void deleteCourseById(String courseId) {
        //删除video中的数据
        boolean flag;
        //删除小节的数据
        videoService.removeByCourseId(courseId);
        //删除章节中的数据
        chapterService.removeByCourseId(courseId);

        //删除课程描述中的数据
        eduCourseDescriptionService.removeById(courseId);

        //删除课程中的数据
        int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new GuliException(20001,"删除失败");
        }
    }
}
