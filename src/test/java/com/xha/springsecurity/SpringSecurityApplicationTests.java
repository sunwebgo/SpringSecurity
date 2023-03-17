package com.xha.springsecurity;

import com.xha.springsecurity.domain.LoginUser;
import com.xha.springsecurity.domain.Menu;
import com.xha.springsecurity.mapper.MenuMapper;
import com.xha.springsecurity.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
class SpringSecurityApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        Object o = redisTemplate.opsForValue().get("login:1");
        System.out.println(o);
    }

    @Test
    public void test1(){
        List<String> menus = menuMapper.selectPermsByUserId(1L);
        System.out.println(menus);
    }

    @Test
    public void test2(){
        String zhangsan = passwordEncoder.encode("zhangsan");
        System.out.println(zhangsan);
    }
}
