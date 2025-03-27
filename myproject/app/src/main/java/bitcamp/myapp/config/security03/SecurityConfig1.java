package bitcamp.myapp.config.security03;

import bitcamp.myapp.member.Member;
import bitcamp.myapp.member.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig1 {

    private static final Log log = LogFactory.getLog(SecurityConfig1.class);

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        log.debug("SecurityFilterChain ready");
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
    public UserDetailsService userDetailsService(MemberService memberService) {
        log.debug("DBUserDetailsService ready");

        // DB 에 저장된 모든 암호를 BCryptPasswordEncoder 를 사용해서 암호화한다
        // 테스트를 위해 한번만 수행한다
//        memberService.changeAllPasswords(passwordEncoder().encode("1111"));

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Member member = memberService.get(username);
                return User.builder()
                        .username(member.getEmail())
                        .password(member.getPassword())
                        .roles("USER")
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("PasswordEncoder ready");

        // Spring 에서 제공하는 PasswordEncoder 사용하기
        return new BCryptPasswordEncoder();

    }

}

