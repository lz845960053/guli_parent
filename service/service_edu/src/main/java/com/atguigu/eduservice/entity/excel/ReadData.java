package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    @ExcelProperty(index = 0,value = "学生编号")
    private String sno;
    @ExcelProperty(index = 1,value = "学生姓名")
    private String  sname;
}
