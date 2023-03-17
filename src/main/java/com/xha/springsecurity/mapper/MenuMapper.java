package com.xha.springsecurity.mapper;

import com.xha.springsecurity.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Tony贾维斯
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2022-11-07 17:21:16
* @Entity com.xha.springsecurity.domain.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    public List<String> selectPermsByUserId(Long userId);
}




