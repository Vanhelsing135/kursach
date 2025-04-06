package com.example.kursach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключаем CSRF (небезопасно в проде)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Разрешаем доступ ко всем эндпоинтам без аутентификации
                .formLogin().disable() // Отключаем форму логина
                .httpBasic().disable(); // Отключаем базовую аутентификацию

        return http.build();
    }
}
