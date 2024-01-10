package com.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.news.persistence.entity.NewsCategoryEntity;
import com.news.persistence.repository.NewsCategoryRepository;

@SpringBootTest
@ActiveProfiles("junit")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NewsCategoryServiceTest {

	@Autowired
	private NewsCategoryRepository newsCategoryRepository;

	@Autowired
	private NewsCategoryService newsCategoryService;

	@Test
	void testGetAll() {

		newsCategoryRepository.saveAll(List.of(new NewsCategoryEntity(1L, "Category1", "Description1"),
				new NewsCategoryEntity(2L, "Category2", "Description2")));

		List<NewsCategoryEntity> result = newsCategoryService.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals("Category1", result.get(0).getTitle());
		assertEquals("Category2", result.get(1).getTitle());
	}

}
