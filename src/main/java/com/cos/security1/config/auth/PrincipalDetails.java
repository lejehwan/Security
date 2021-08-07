package com.cos.security1.config.auth;

// 시큐리티가 /login 주소 요청을 낚아 채서 로그인을 진행시킴.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어 줌(Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함.
// User 오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 일반 로그인 -> UserDetails, 구글 로그인 -> OAuth2User 타입이므로 2가지의 로그인 방식의 타입을 동일하게 하기 위해
// PrincipalDetails 에 두 타입을 implements 해서 Authentication(시큐리티 세션) 사용
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;// 콤포지션
    private Map<String,Object> attributes;

    // 일반 로그인
    public PrincipalDetails(User user){
        this.user=user;
    }

    // OAuth 로그인
    public PrincipalDetails(User user, Map<String,Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
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

        // 우리 사이트에서 1년 동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 했다면,
        // 현재 시간 - 로그인 시간 => 1년을 초과하면 return false;로 설정 가능

        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String,Object> getAttributes() {
        return attributes;
    }
}
