package com.xha.springsecurity.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 当解析的json串中含有类里未定义的属性时，加上@JsonIgnoreProperties(ignoreUnkown = true)注解，
 * 可以按照类中已存在的属性将json串反序列化为对应的object；
 * 若无@JsonIgnoreProperties(ignoreUnkown = true)注解，
 * 将含有未知属性的json串反序列化为object时会失败。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails {

    private User user;
//  存储权限信息
    private List<String> permissions;

    public LoginUser(User user,List<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * SimpleGrantedAuthority不支持序列化，
     * 所以不需要将其序列化，不存放到redis中
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null){
            return authorities;
        }
//        1.把permissions中String类型的权限信息封装为SimpleGrantedAuthority对象
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
