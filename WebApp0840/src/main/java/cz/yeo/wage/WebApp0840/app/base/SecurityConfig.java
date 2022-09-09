package cz.yeo.wage.WebApp0840.app.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// # SpringSecurty 사용을 위한 설정
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // # 모든 권한을 풀어준다, 로그인 안해도 됨
        http.authorizeRequests().antMatchers("/**").permitAll();
        return http.build();
    }
}
