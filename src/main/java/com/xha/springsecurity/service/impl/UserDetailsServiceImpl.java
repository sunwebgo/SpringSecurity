package com.xha.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xha.springsecurity.domain.LoginUser;
import com.xha.springsecurity.domain.User;
import com.xha.springsecurity.mapper.MenuMapper;
import com.xha.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
//        1.查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = null;
        try {
            user = userService.getOne(queryWrapper);
        } catch (Exception e) {
            log.info("获取用户信息失败！");
        }

//    2.添加用户对应的权限信息
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());
//        3.把数据封装为UserDetails返回
        return new LoginUser(user, perms);
    }
}
