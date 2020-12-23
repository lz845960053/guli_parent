package com.atguigu.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.atguigu.eduservice.Demo.ExcelListener;
import com.atguigu.eduservice.entity.excel.DemoData;
import com.atguigu.eduservice.entity.excel.ReadData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelDemo {

    private static List<DemoData> data(){
        List<DemoData> list = new ArrayList<>();
        for(int i = 0; i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("张三"+i);
            list.add(demoData);
        }
        return list;
    }

    //写方法二
    @Test
    public  void test1(){
        //路径
        String fileName = "D:\\11.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("写入方法二").build();
        excelWriter.write(data(),writeSheet);
        excelWriter.finish();
    }
    //写方法一
    @Test
    public void test2(){
        //路径
        String fileName = "D:\\11.xlsx";
        EasyExcel.write(fileName,DemoData.class).sheet("写入方法1").doWrite(data());
    }

    //读
    @Test
    public void test3(){
        String fileName = "D:\\11.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ReadData.class,new ExcelListener()).sheet().doRead();
    }

    @Test
    public void test4(){
        String str = "dzx_979_1";
        String[] s = str.split("\\_");
        System.out.println(s[s.length-1]);
        for (String s1 : s) {
            System.out.println(s1);
        }

    }
}
