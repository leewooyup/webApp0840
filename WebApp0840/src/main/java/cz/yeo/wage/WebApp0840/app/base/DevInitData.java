package cz.yeo.wage.WebApp0840.app.base;

import cz.yeo.wage.WebApp0840.app.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevInitData {
    @Bean
    CommandLineRunner init(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {

        };
    }
}
