package com.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.news.persistence.entity.NewsCategoryEntity;
import com.news.persistence.entity.NewsEntity;
import com.news.persistence.repository.NewsCategoryRepository;
import com.news.rest.dto.CreationNewsDTO;

@SpringBootTest
@ActiveProfiles("junit")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NewsServiceTest {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsCategoryRepository newsCategoryRepository;

	@Test
	@Transactional
	void testGetAll() {

		createTestCategory(1L, "Category 1");
		createTestCategory(2L, "Category 2");

		newsService.create(createTestCreationNewsDTO("Test Title 1", "Test Description 1", 1L));
		newsService.create(createTestCreationNewsDTO("Test Title 2", "Test Description 2", 2L));

		PageRequest pageable = PageRequest.of(0, 10);
		Page<NewsEntity> pageOfNews = newsService.getAll(pageable);

		assertEquals(2, pageOfNews.getTotalElements());
		assertEquals(2, pageOfNews.getContent().size());
		assertEquals("Test Title 1", pageOfNews.getContent().get(0).getTitle());
		assertEquals("Test Title 2", pageOfNews.getContent().get(1).getTitle());
	}

	@Test
	@Transactional
	void testCreateAndGet() {

		createTestCategory(1L, "Test Category");

		List<NewsCategoryEntity> categories = newsCategoryRepository.findAll();
		assertEquals(1, categories.size());
		assertEquals("Test Category", categories.get(0).getTitle());

		CreationNewsDTO creationNewsDTO = createTestCreationNewsDTO(1L);
		newsService.create(creationNewsDTO);

		NewsEntity createdNews = newsService.get(1L);

		assertNotNull(createdNews);
		assertEquals(creationNewsDTO.getTitle(), createdNews.getTitle());
		assertEquals(creationNewsDTO.getDescription(), createdNews.getDescription());
		assertEquals(1L, createdNews.getCategory().getId());
	}

	@Test
	@Transactional
	void testUpdate() {

		createTestCategory(1L, "Test Category");

		CreationNewsDTO creationNewsDTO = createTestCreationNewsDTO(1L);
		newsService.create(creationNewsDTO);

		NewsEntity updatedNews = newsService.update(1L,
				createTestCreationNewsDTO("Updated Title", "Updated Description", 1L));

		assertNotNull(updatedNews);
		assertEquals("Updated Title", updatedNews.getTitle());
		assertEquals("Updated Description", updatedNews.getDescription());
	}

	@Test
	@Transactional
	void testDelete() {

		createTestCategory(1L, "Test Category");

		CreationNewsDTO creationNewsDTO = createTestCreationNewsDTO(1L);
		newsService.create(creationNewsDTO);

		newsService.delete(1L);

		assertThrows(RuntimeException.class, () -> newsService.get(1L));
	}

	@Test
	@Transactional
	void testGetNewsByCategory() {

		createTestCategory(1L, "Category 1");
		createTestCategory(2L, "Category 2");

		newsService.create(createTestCreationNewsDTO("Test Title 1", "Test Description 1", 1L));
		newsService.create(createTestCreationNewsDTO("Test Title 2", "Test Description 2", 2L));

		List<NewsEntity> newsInCategory1 = newsService.getNewsByCategory(1L);
		List<NewsEntity> newsInCategory2 = newsService.getNewsByCategory(2L);

		assertEquals(1, newsInCategory1.size());
		assertEquals(1, newsInCategory2.size());
	}

	private void createTestCategory(Long id, String title) {
		NewsCategoryEntity category = NewsCategoryEntity.builder().id(id).title(title).description("Test Description")
				.build();
		newsCategoryRepository.save(category);
	}

	private CreationNewsDTO createTestCreationNewsDTO(Long categoryId) {
		return createTestCreationNewsDTO("Test Title", "Test Description", categoryId);
	}

	private CreationNewsDTO createTestCreationNewsDTO(String title, String description, Long categoryId) {
		return CreationNewsDTO.builder().title(title).description(description).categoryId(categoryId).build();
	}
}
