package cz.yeo.wage.WebApp0840;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebApp0840Application {

	public static void main(String[] args) {
		SpringApplication.run(WebApp0840Application.class, args);
	}

}
