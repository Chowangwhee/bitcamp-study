package bitcamp.myapp.config.security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
// @EnableWebSecurity : Spring Boot 에서는 자동으로 활성화 된다
public class SecurityConfig1 {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 1. /home, css 디렉터리의 내용에 관한 요청은 인증을 검사하지 않게 설정
        http.authorizeHttpRequests()
                .mvcMatchers("/home", "/css/**").permitAll()
                .anyRequest().authenticated();

        // 위에서 설정한 대로 작업할 필터 체인을 구성
        return http.build();
    }

}
