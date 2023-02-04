package cz.yeo.wage.WebApp0840;

import cz.yeo.wage.WebApp0840.app.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"base-addi", "test"})
class WebApp0840ApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("회원의 수")
	void t2() throws Exception {
		long count = userService.count();
		assertThat(count).isGreaterThan(0);
	}

}
