package com.xha.springsecurity.authority;

import com.xha.springsecurity.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("authority")
public class MyAuthority {

    public boolean hasAuthority(String authority){
//        1.获取当前用户的权限
//        2.因为用户信息已经封装到SecurityContextHolder对象当中
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        3.获取到当前用户的权限列表
        List<String> permissions = loginUser.getPermissions();
//        4.判断用户权限列表中是否含有方法级安全管理定义的authority
        return permissions.contains(authority);
    }
}
