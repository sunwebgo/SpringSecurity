package com.xha.springsecurity.controller;

import com.xha.springsecurity.domain.ResponseResult;
import com.xha.springsecurity.domain.User;
import com.xha.springsecurity.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 用户退出
     * @return
     */
    @GetMapping("/quit")
    public ResponseResult userQuit() {
        return userService.quit();
    }

}
