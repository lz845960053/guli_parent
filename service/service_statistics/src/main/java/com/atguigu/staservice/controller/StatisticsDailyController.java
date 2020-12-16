package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
    * @Params day
    * @Desc 统计数据，保存至统计表中
    * */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable("day") String day){
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

}

