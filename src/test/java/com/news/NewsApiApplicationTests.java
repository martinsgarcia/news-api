package com.news;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("junit")
class NewsApiApplicationTests {

	@Test
	void contextLoads() {
		String[] args = { "--spring.profiles.active=junit" };
		assertDoesNotThrow(() -> NewsApiApplication.main(args));
	}

}
