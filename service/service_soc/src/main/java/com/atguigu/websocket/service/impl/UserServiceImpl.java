package com.atguigu.websocket.service.impl;

import com.atguigu.websocket.entity.User;

import com.atguigu.websocket.mapper.UserMapper;

import com.atguigu.websocket.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理-用户基础信息表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
