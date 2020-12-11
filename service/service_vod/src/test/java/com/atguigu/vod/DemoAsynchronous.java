package com.atguigu.vod;

import org.apache.poi.hssf.record.DVALRecord;
import org.junit.Test;

import java.util.concurrent.*;

public class DemoAsynchronous {

    int count =20;//库存
    int sumPare=100; ///要求达到的订单价格
    //创建线程i
    private ExecutorService executorService =  Executors.newCachedThreadPool();
    @Test
    public void test(){
        String orderName = "华为手机mate40";
        int parce = 120;
        System.out.println("获取到订单信息"+orderName);
        //判断库存的充足情况
        boolean kucun=count>0?true:false;
        //判断是否能够进行订单折扣
        boolean flag=parce>=sumPare?true:false;
        //进行下单
        if(kucun && flag){
            System.out.println("下单成功，订单名："+orderName+"订单价格："+parce);
        }else{
            System.out.println("下单失败"+orderName);
        }

    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {

        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i+"##!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("测试开始####################");
        //创建两个线程
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //任务一 CompletableFuture
        CompletableFuture future1 =CompletableFuture.supplyAsync(()->{
            try {
                System.out.println("任务一：查询库存状态，大概需要8秒...");
                //省略业务判断方法.......
                Thread.sleep(10000);
                System.out.println("任务一：库存查询结束...");
                return true;
            } catch (InterruptedException e) {
                System.err.println("查询出错");
                return false;
            }
            },executor);
        //任务二：查询价格是否符合下单
        CompletableFuture future2=CompletableFuture.supplyAsync(()->{
            try {
                System.out.println("任务二：查询订单价格是否符合要求，大概需要5秒...");
                //省略业务判断价格是否符合下单.......
                Thread.sleep(5000);
                System.out.println("任务二：订单价格查询结束...");
                return true;
            } catch (InterruptedException e) {
                System.err.println("查询出错");
                return false;
            }
        },executor);


        future1.thenAccept((e)->{
            System.out.println("任务一：查询结果是:"+(boolean)e);
        });

        future2.thenAccept((e)->{System.out.println("任务二：查询结果是:"+(boolean)e);});

        System.out.println("等待查询结果中");
        boolean one= (boolean) future1.get();
        boolean two= (boolean) future2.get();
        if( one && two ){
            System.out.println("下单成功！");
        }else{
            System.out.println("下单失败！");
        }

    }
}
