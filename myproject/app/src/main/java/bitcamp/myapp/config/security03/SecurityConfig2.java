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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 학습목표:
// - Spring Security 에서 인증을 수행한 후 세션 처리를 위해 페이지 컨트롤러에게 넘기기
// -- formLogin() 설정 변경
// --- successForwardUrl("/auth/login")
// -- logout() 설정 추가

//@Configuration
public class SecurityConfig2 {

    private static final Log log = LogFactory.getLog(SecurityConfig2.class);

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
                .formLogin()
                    .successForwardUrl("/auth/login") // 고르인 성공 후 페이지 컨트롤러로 포워딩
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/auth/login")
                    .permitAll()
                    .and()
                // 3. 로그아웃 설정 - formLogin() 을 커스터마이징한다면 logout() 경로가 비활성화 된다
                // 따라서 다음과 같이 명시적으로 설정해야 한다
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                    .logoutUrl("/logout") POST 요청에서만 동작
                    .logoutSuccessUrl("/home")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                // 4. CSRF(Cross-Site Request Forgery) 기능 비활성화
                // 로그아웃 시 세션을 무효화시키면 세션에 보관된 CSRF 토큰도 함께 삭제된다
                // 클라이언트 요청 내용에 유효한 CSRF 토큰이 없을 경우 CSRF 공격으로 간주하고 요청을 거절한다
//                .csrf().disable()
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

