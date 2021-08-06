package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity// 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
public class SecurityConfig {
}
