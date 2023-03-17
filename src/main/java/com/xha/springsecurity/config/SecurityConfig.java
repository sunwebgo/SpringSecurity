package com.xha.springsecurity.config;

import com.xha.springsecurity.filter.JwtAuthenticationTokenFilter;
import com.xha.springsecurity.hander.AccessDeniedHandlerImpl;
import com.xha.springsecurity.hander.AuthenticationEntryPointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter tokenFilter;

    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;


    /**
     * 明文密文
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager实现认证
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 全局配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问，即在未登录状态下可以访问，登录状态不能访问
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/hello").hasAuthority("system:dept:list")
//                permitAll规定登录和非登录状态都能访问
//                .antMatchers("/静态资源").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
//        将token校验过滤器放到过滤器链之前
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
//        配置异常处理器
        http.exceptionHandling()
//                认证异常处理器
                .authenticationEntryPoint(authenticationEntryPoint)
//                授权异常处理器
                .accessDeniedHandler(accessDeniedHandler);
//        允许跨域
        http.cors();
    }
}
