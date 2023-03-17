package com.xha.springsecurity.mapper;

import com.xha.springsecurity.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Tony贾维斯
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-11-06 11:23:58
* @Entity com.xha.springsecurity.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




