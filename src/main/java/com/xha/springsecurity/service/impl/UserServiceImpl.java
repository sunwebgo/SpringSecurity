package com.xha.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xha.springsecurity.domain.LoginUser;
import com.xha.springsecurity.domain.ResponseResult;
import com.xha.springsecurity.domain.User;
import com.xha.springsecurity.mapper.UserMapper;
import com.xha.springsecurity.service.UserService;
import com.xha.springsecurity.utils.JwtUtils;
import com.xha.springsecurity.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tony贾维斯
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2022-11-06 11:23:58
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword());

//        1.采用AuthenticationManager进行认证,调用authenticate方法进行认证，再调用loadUserByUsername查询用户，
//        返回UserDetails对象(LoginUser实现了UserDetails接口)，并将UserDetails中的权限信息设置到Authentication对象中
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        2.认证未通过给出提示
        if (ObjectUtils.isEmpty(authenticate)) {
            throw new RuntimeException("登录失败");
        }
//        3.通过Authentication对象获取到LoginUser对象，Principal是Authentication中的方法
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        4.获取到用户id
        String userId = loginUser.getUser().getId().toString();
//        5.生成jwt
        String jwt = JwtUtils.createJWT(userId);
        Map<String, String> map = new HashMap<>();
//        6.以”token“为key，jwt为value返回给前端
        map.put("token", jwt);
//        4.将完整的用户信息（包括权限信息）写入redis，userId作为key
        redisCache.setCacheObject("login:" + userId, loginUser);
        return new ResponseResult(200, "登录成功", map);
    }

    /**
     * 用户退出
     * @return
     */
    public ResponseResult quit() {
//        1.获取SecurityContentHolder中用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        2.删除redis中数据
        redisCache.deleteObject("login:" + loginUser.getUser().getId());
        return new ResponseResult(200,"注销成功");
    }


}




