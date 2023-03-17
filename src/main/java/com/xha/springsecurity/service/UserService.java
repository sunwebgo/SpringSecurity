package com.xha.springsecurity.service;

import com.xha.springsecurity.domain.ResponseResult;
import com.xha.springsecurity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Tony贾维斯
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-11-06 11:23:58
*/
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult quit();
}
