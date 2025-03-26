package bitcamp.myapp.config.security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
// @EnableWebSecurity : Spring Boot 에서는 자동으로 활성화 된다
public class SecurityConfig2 {

    public SecurityConfig2() {
        System.out.println("Security Config Constructor");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 1. Spring Security 에서 제공하는 기본 로그인 폼 대신에 사용자 정의 로그인 폼 사용
        //    로그인 폼 설정 객체 준비: 익명 클래스
        Customizer<FormLoginConfigurer<HttpSecurity>> obj = new Customizer<FormLoginConfigurer<HttpSecurity>>() {
            @Override
            public void customize(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
                formLoginConfigurer.loginPage("/auth/login-form"); // 로그인 폼 URL 설정
                formLoginConfigurer.loginProcessingUrl("/auth/login"); // 로그인 처리 URL 설정
                formLoginConfigurer.usernameParameter("email"); // 기본값 = "username"
                formLoginConfigurer.passwordParameter("password"); // 기본값 = "password"
                formLoginConfigurer.defaultSuccessUrl("/home"); // 로그인 성공 후 리다이렉트 URL 설정
//                formLoginConfigurer.permitAll(); // 모든 권한 부여
            }
        };

        HttpSecurity httpSecurity = http.formLogin(obj);
        
        SecurityFilterChain securityFilterChain = httpSecurity.build();
        return securityFilterChain;
    }

}
