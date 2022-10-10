package cz.yeo.wage.WebApp0840.app.base;

import cz.yeo.wage.WebApp0840.app.user.UserService;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
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
            String password = passwordEncoder.encode("1234");
            SiteUser siteUser1 = userService.join("user1", password, "peter", "user1@test.com");
            SiteUser siteUser2 = userService.join("user2", password, "nick", "user2@test.com");
            userService.setProfileImgByUrl(siteUser1, "https://i.imgur.com/tsRWp6H.png");
            userService.setProfileImgByUrl(siteUser2, "https://i.imgur.com/tsRWp6H.png");
        };
    }
}
