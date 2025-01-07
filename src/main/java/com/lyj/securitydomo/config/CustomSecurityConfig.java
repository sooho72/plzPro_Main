package com.lyj.securitydomo.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

    // UserDetailsService는 사용자 인증 정보를 가져오는 서비스로, Security 설정에 사용
    private final UserDetailsService userDetailsService;

    // SecurityFilterChain을 설정하여 각 요청의 보안 규칙을 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // FORWARD 요청 허용
                        .requestMatchers("/login", "/signup", "/user/login", "/user/signup", "/user/register","/user/join","/error").permitAll() // 로그인 및 회원가입 경로 허용
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/webjars/**").permitAll() // 정적 리소스 허용
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/user/login") // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/loginProcess") // 로그인 처리 URL
                        .defaultSuccessUrl("/posting/list") // 로그인 성공 시 이동할 경로
                        .failureUrl("/user/login?error=true") // 로그인 실패 시 이동할 경로
                        .permitAll() // 로그인 페이지는 모든 사용자 접근 허용
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .clearAuthentication(true) // 인증 정보 제거
                        .permitAll() // 로그아웃은 모든 사용자 접근 허용
                );

        // 사용자 인증 관리 설정
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
