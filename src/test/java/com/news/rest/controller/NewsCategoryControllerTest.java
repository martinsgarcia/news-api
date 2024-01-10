package com.news.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.persistence.entity.NewsCategoryEntity;
import com.news.persistence.entity.NewsEntity;
import com.news.rest.dto.NewsCategoryDTO;
import com.news.service.NewsCategoryService;
import com.news.service.NewsService;

class NewsCategoryControllerTest {

	private MockMvc mockMvc;

	@Mock
	private NewsCategoryService newsCategoryService;

	@Mock
	private NewsService newsService;

	@InjectMocks
	private NewsCategoryController newsCategoryController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(newsCategoryController).build();
	}

	@Test
	void testGetAll() throws Exception {

		NewsCategoryEntity category1 = new NewsCategoryEntity(1L, "Category 1", "Description 1");
		NewsCategoryEntity category2 = new NewsCategoryEntity(2L, "Category 2", "Description 2");
		List<NewsCategoryEntity> categories = Arrays.asList(category1, category2);

		when(newsCategoryService.getAll()).thenReturn(categories);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/category").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].title", is("Category 1")))
				.andExpect(jsonPath("$[0].description", is("Description 1"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].title", is("Category 2")))
				.andExpect(jsonPath("$[1].description", is("Description 2")));
	}

	@Test
	void testGet() throws Exception {

		NewsCategoryEntity category = new NewsCategoryEntity(1L, "Category 1", "Description 1");
		when(newsCategoryService.get(anyLong())).thenReturn(category);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", is("Category 1")))
				.andExpect(jsonPath("$.description", is("Description 1")));
	}

	@Test
	void testGetByCategories() throws Exception {

		NewsEntity news1 = new NewsEntity(1L, "News 1", "Description 1",
				new NewsCategoryEntity(1L, "Category 1", "Description 1"));

		NewsEntity news2 = new NewsEntity(2L, "News 2", "Description 2",
				new NewsCategoryEntity(1L, "Category 1", "Description 1"));

		List<NewsEntity> newsEntities = Arrays.asList(news1, news2);

		when(newsService.getNewsByCategory(anyLong())).thenReturn(newsEntities);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/category/1/news").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].title", is("News 1")))
				.andExpect(jsonPath("$[0].description", is("Description 1"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].title", is("News 2")))
				.andExpect(jsonPath("$[1].description", is("Description 2")));
	}

	@Test
	void testCreate() throws Exception {

		NewsCategoryDTO newsCategoryDTO = new NewsCategoryDTO(null, "Category 1", "Description 1");
		doNothing().when(newsCategoryService).create(any());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/category").content(asJsonString(newsCategoryDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testUpdate() throws Exception {

		NewsCategoryDTO newsCategoryDTO = new NewsCategoryDTO(null, "Updated Category", "Updated Description");
		NewsCategoryEntity updatedCategory = new NewsCategoryEntity(1L, "Updated Category", "Updated Description");
		when(newsCategoryService.update(anyLong(), any())).thenReturn(updatedCategory);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/category/1").content(asJsonString(newsCategoryDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Updated Category")))
				.andExpect(jsonPath("$.description", is("Updated Description")));
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
