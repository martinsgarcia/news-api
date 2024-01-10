package com.news;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.news.service.NewsService;

@SpringBootTest
@ActiveProfiles("junit")
class NewsApiApplicationTests {

	@Autowired
	private NewsService service;

	@Test
	void contextLoads() {
		assertNotNull(service);
	}

}
