package bitcamp.myapp.config.security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
// @EnableWebSecurity : Spring Boot 에서는 자동으로 활성화 된다
public class SecurityConfig1 {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                // 1. 요청 URL의 접근권한 설정
                .authorizeHttpRequests()
                .mvcMatchers("/home", "/css/**").permitAll() // /home 과 css 디렉터리 요청은 접근허용
                .anyRequest().authenticated() // 그 외 요청 접근에 대해 인증 자격을 확인한다
                .and() // HttpSecurity 객체를 반환하는 메서드
                // 2. 인가되지 않은 요청인 경우 Spring Security 기본 로그인 URL 주소로 리다이렉트
                .formLogin(Customizer.withDefaults())
                .build();
    }

}
