package com.atguigu.eduservice.entity.vo;

import lombok.Data;

@Data
public class TeacherQuery {
    //封装讲师名称name，讲师头衔level、讲师入驻时间gmt_create（时间段）查询

    private String name;

    private Integer level;

    private String begin;

    private String end;
}
