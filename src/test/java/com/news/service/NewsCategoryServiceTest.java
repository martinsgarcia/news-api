package com.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.news.persistence.entity.NewsCategoryEntity;
import com.news.persistence.repository.NewsCategoryRepository;
import com.news.rest.dto.NewsCategoryDTO;

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

		assertEquals(2, result.size());
		assertEquals("Category1", result.get(0).getTitle());
		assertEquals("Category2", result.get(1).getTitle());
	}

	@Test
	void testGet() {

		newsCategoryRepository.save(new NewsCategoryEntity(1L, "Category1", "Description1"));

		NewsCategoryEntity result = newsCategoryService.get(1L);

		// Assert
		assertEquals("Category1", result.getTitle());
	}

	@Test
	void testCreate() {

		NewsCategoryDTO newsCategoryDTO = new NewsCategoryDTO();
		newsCategoryDTO.setId(1L);
		newsCategoryDTO.setTitle("Category1");
		newsCategoryDTO.setDescription("Description1");

		newsCategoryService.create(newsCategoryDTO);

		NewsCategoryEntity result = newsCategoryRepository.findById(1L).orElse(null);

		assertNotNull(result);
		assertEquals("Category1", result.getTitle());
		assertEquals("Description1", result.getDescription());
	}

	@Test
	void testUpdate() {

		newsCategoryRepository.save(new NewsCategoryEntity(1L, "Category1", "Description1"));

		NewsCategoryDTO newsCategoryDTO = new NewsCategoryDTO();
		newsCategoryDTO.setTitle("UpdatedCategory");
		newsCategoryDTO.setDescription("UpdatedDescription");

		NewsCategoryEntity result = newsCategoryService.update(1L, newsCategoryDTO);

		// Assert
		assertEquals("UpdatedCategory", result.getTitle());
		assertEquals("UpdatedDescription", result.getDescription());
	}

	@Test
	void testDelete() {

		newsCategoryRepository.save(new NewsCategoryEntity(1L, "Category1", "Description1"));

		newsCategoryService.delete(1L);

		NewsCategoryEntity result = newsCategoryRepository.findById(1L).orElse(null);

		assertNull(result);
	}

}
