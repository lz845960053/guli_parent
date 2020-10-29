package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-24
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录接口
        @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        //调用service 返回token
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }
    //注册的接口
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        int insert = memberService.registerUser(registerVo);
        if(insert>0){
            return R.ok();
        }else{
            return R.error().message("注册失败");
        }

    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
            //调用工具类方法，根据request返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据memberid查询用户
        UcenterMember member = memberService.getById(memberId);
        //将用户信息返回前端
        return R.ok().data("member",member);
    }

}

