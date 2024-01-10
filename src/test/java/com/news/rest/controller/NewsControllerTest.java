package com.news.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.persistence.entity.NewsEntity;
import com.news.rest.dto.CreationNewsDTO;
import com.news.service.NewsService;

class NewsControllerTest {

	private MockMvc mockMvc;

	@Mock
	private NewsService newsService;

	@InjectMocks
	private NewsController newsController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders

				.standaloneSetup(newsController)

				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())

				.build();
	}

	@Test
	void testGetAll() throws Exception {

		NewsEntity news1 = new NewsEntity(1L, "News 1", "Description 1", null);
		NewsEntity news2 = new NewsEntity(2L, "News 2", "Description 2", null);

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));

		Page<NewsEntity> newsPage = new PageImpl<>(Arrays.asList(news1, news2));
		newsPage = new PageImpl<>(newsPage.getContent(), pageRequest, newsPage.getTotalElements());

		when(newsService.getAll(pageRequest)).thenReturn(newsPage);

		ResultActions results = mockMvc.perform(MockMvcRequestBuilders.get("/api/news").param("page", "0")
				.param("size", "10").param("sort", "id,asc").contentType(MediaType.APPLICATION_JSON));

		results.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title", is("News 1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description", is("Description 1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id", is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title", is("News 2")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].description", is("Description 2")));
	}

	@Test
	void testGet() throws Exception {

		NewsEntity news = new NewsEntity(1L, "News 1", "Description 1", null);
		when(newsService.get(anyLong())).thenReturn(news);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("News 1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Description 1")));
	}

	@Test
	void testCreate() throws Exception {

		CreationNewsDTO creationNewsDTO = new CreationNewsDTO("News 1", "Description 1", 1L);
		doNothing().when(newsService).create(any());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/news").content(asJsonString(creationNewsDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testUpdate() throws Exception {

		CreationNewsDTO creationNewsDTO = new CreationNewsDTO("Updated News", "Updated Description", 1L);
		NewsEntity updatedNews = new NewsEntity(1L, "Updated News", "Updated Description", null);
		when(newsService.update(anyLong(), any())).thenReturn(updatedNews);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/news/1").content(asJsonString(creationNewsDTO))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Updated News")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Updated Description")));
	}

	@Test
	void testDelete() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
