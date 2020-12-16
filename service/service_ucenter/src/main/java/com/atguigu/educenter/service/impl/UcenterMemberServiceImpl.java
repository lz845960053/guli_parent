package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-24
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //登录接口
    @Override
    public String login(UcenterMember member) {
        //对传入的数据进行非空判断
        String password = member.getPassword();
        String mobile = member.getMobile();
        if(StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)){
            throw new GuliException(20001,"为空");
        }
        //根据手机号查询用户信息，非空判断
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if(ucenterMember == null){
            throw new GuliException(20001,"无此用户");
        }
        //对传入的密码和存储的密码进行对比
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }
        //判断用户是否可用
        if(ucenterMember.getIsDisabled()){
            throw new GuliException(20001,"该用户禁用");
        }
        //生成token
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }
    //注册的接口
    @Override
    public int registerUser(RegisterVo registerVo) {
        int insert = 0;
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //1,判断是否为空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"数据为空");
        }
        //2,判断手机号是否已经存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"用户已存在");
        }
        //3,判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new GuliException(20001,"验证码过期或错误");
        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);//用户不禁用
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        //4，存储数据
         insert = baseMapper.insert(ucenterMember);
         return insert;
    }

    /**
     * @params day
     * @desc 统计某一天的注册人数
     * */
    @Override
    public Integer registerCountByDay(String day) {
        Integer count = baseMapper.selectRegisterCount(day);
        return count;
    }
}
