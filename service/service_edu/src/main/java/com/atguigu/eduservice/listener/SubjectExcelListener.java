package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    //一行一行去读取excle内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"表格为空！");
        }else{
            //判断是否有一级分类
            EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
            if(existOneSubject == null){//没有相同一级分类，进行添加
                existOneSubject = new EduSubject();
                existOneSubject.setParentId("0");
                existOneSubject.setTitle(subjectData.getOneSubjectName());
                subjectService.save(existOneSubject);
            }
            String pid = existOneSubject.getId();
            //判断是否有二级分类
            EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
            if(existTwoSubject == null){//没有相同一级分类，进行添加
                existTwoSubject = new EduSubject();
                existTwoSubject.setParentId(pid);
                existTwoSubject.setTitle(subjectData.getTwoSubjectName());
                subjectService.save(existTwoSubject);
            }

        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //查询一级分类的方法
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id","0");
        eduSubjectQueryWrapper.eq("title",name);
        EduSubject oneSubject = subjectService.getOne(eduSubjectQueryWrapper);
        return oneSubject;
    }
    //查询二级分类的方法
    private EduSubject existTwoSubject (EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id",pid);
        eduSubjectQueryWrapper.eq("title",name);
        EduSubject twoSubject = subjectService.getOne(eduSubjectQueryWrapper);
        return twoSubject;
    }

}
