package bitcamp.myapp.config.security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig2 {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        System.out.println("SecurityFilterChain ready");
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
    // 사용자 인증을 수행할 객체를 준비
    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("UserDetailsService ready");

        // 임시 사용자 정보 생성
        // 사용자가 입력한 암호는 특별한 알고리즘으로 가공하여 저장
        // 로그인 시 암호 원문을 똑같은 알고리즘으로 가공하여 비교
        UserDetails[] userdetails = {
                User.withDefaultPasswordEncoder().username("user1@test.com").password("1111").roles("USER").build(),
                User.withDefaultPasswordEncoder().username("user2@test.com").password("1111").roles("USER").build(),
                User.withDefaultPasswordEncoder().username("user3@test.com").password("1111").roles("USER").build(),
        };

        // 메모리 영역에 사용자 목록을 준비하고 검사를 수행하는 객체 반환
        return new InMemoryUserDetailsManager(userdetails);
    }
}
