package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter") //注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致
public interface UcenterClient {

    @GetMapping("/educenter/member/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day);
}
