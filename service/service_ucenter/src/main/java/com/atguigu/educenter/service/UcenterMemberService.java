package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-24
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //登录接口
    String login(UcenterMember member);
    //注册的接口
    int registerUser(RegisterVo registerVo);

    /**
     * @params day
     * @desc 统计某一天的注册人数
     * */
    Integer registerCountByDay(String day);
}
