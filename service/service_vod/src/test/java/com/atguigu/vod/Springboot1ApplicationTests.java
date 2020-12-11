package com.atguigu.vod;


import com.atguigu.vod.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot1ApplicationTests {
    @Autowired
    private VideoService videoService;

    @Test
    public void contextLoads(){

    }

    @Test
    public void testVoid() throws InterruptedException {
        for(int i=0;i<20;i++){
            videoService.excutVoidTask(i);
        }
        System.out.println("========主线程执行完毕=========");
    }

    @Test
    public void testReturn() throws InterruptedException, ExecutionException {
        List<Future<String>> lstFuture = new ArrayList<>();
        for(int i=0;i<100;i++){
            while(true){
                try {
                    Future<String> stringFuture = videoService.excuteValueTask(i);
                    lstFuture.add(stringFuture);
                    break;
                } catch (InterruptedException e) {
                    System.out.println("线程池满，等待1S。");
                    Thread.sleep(1000);
                    e.printStackTrace();
                }

            }
        }
        // 获取值.get是阻塞式，等待当前线程完成才返回值
        for (Future<String> future : lstFuture) {
            System.out.println(future.get());
        }
        System.out.println("========主线程执行完毕=========");

    }
}
