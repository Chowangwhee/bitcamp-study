package bitcamp.myapp.config.security03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
// @EnableWebSecurity : Spring Boot 에서는 자동으로 활성화 된다
public class SecurityConfig1 {

    public SecurityConfig1() {
        System.out.println("Security Config Constructor");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 1. Spring Security 에서 제공하는 기본 로그인 폼 대신에 사용자 정의 로그인 폼 사용하는 필터 설정
        //    로그인 폼 설정 객체 준비: 익명 클래스를 파라미터에 직접 적용
        http.formLogin(formLoginConfigurer -> {
            formLoginConfigurer.loginPage("/auth/login-form"); // 로그인 폼 URL 설정
            formLoginConfigurer.loginProcessingUrl("/auth/login"); // 로그인 처리 URL 설정
            formLoginConfigurer.usernameParameter("email"); // 기본 파라미터 값 = "username"
            formLoginConfigurer.passwordParameter("password"); // 기본 파라미터 값 = "password"
            formLoginConfigurer.defaultSuccessUrl("/home"); // 로그인 성공 후 리다이렉트 URL 설정
            formLoginConfigurer.permitAll(); // 모든 권한 부여
        });
        // 2. 접근 권한 검사(인가; authorization) 필터 설정
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .anyRequest()
                    .authenticated(); // 모든 요청에 대해 권한 검사를 수행하여 로그인 상태가 아닐 경우 로그인 폼으로 리다이렉트 시킨다
        });

        // SecurityFilterChain 준비
        return http.build();
    }

}
