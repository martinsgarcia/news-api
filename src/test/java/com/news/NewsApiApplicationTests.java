package com.news;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "junit")
class NewsApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
