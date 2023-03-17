package com.xha.springsecurity.filter;

import com.xha.springsecurity.domain.LoginUser;
import com.xha.springsecurity.utils.JwtUtils;
import com.xha.springsecurity.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;


    /**
     * 解析token过滤器
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        1.获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
//            1.1因为没有token，放行。
            filterChain.doFilter(request, response);
            return;
        }
//        2.解析token获取其中的userId
        String userId = null;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
//        3.从redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }

//        4.通过LoginUser对象获取权限信息封装到Authentication对象中
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());

//        5.封装Authentication对象存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        5.放行
        filterChain.doFilter(request, response);
    }
}
