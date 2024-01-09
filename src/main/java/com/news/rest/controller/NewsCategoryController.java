package com.news.rest.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.persistence.entity.NewsCategoryEntity;
import com.news.rest.dto.NewsCategoryDTO;
import com.news.service.NewsCategoryService;
import com.news.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "News Category API", description = "The News Category API")
@RestController
@RequestMapping(value = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsCategoryController {

	private final NewsCategoryService newsCategoryService;
	private final NewsService newsService;

	public NewsCategoryController(NewsCategoryService newsCategoryService, NewsService newsService) {
		this.newsCategoryService = newsCategoryService;
		this.newsService = newsService;
	}

	@Operation(summary = "Fetch all categories", description = "fetches all categories entities and their data from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@GetMapping
	public List<NewsCategoryDTO> getAll() {

		return newsCategoryService.getAll()

				.stream()

				.map(newsCategoryEntity -> NewsCategoryDTO.builder()

						.id(newsCategoryEntity.getId())

						.title(newsCategoryEntity.getTitle())

						.description(newsCategoryEntity.getDescription())

						.build()

				)

				.toList();
	}

	@Operation(summary = "Get Category", description = "Get Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@GetMapping(value = "/{newsCategoryId}")
	public NewsCategoryDTO get(@PathVariable(name = "newsCategoryId") Long newsCategoryId) {

		NewsCategoryEntity newsCategoryEntity = newsCategoryService.get(newsCategoryId);

		return NewsCategoryDTO

				.builder()

				.title(newsCategoryEntity.getTitle())

				.description(newsCategoryEntity.getDescription())

				.build();
	}

	@Operation(summary = "Fetch all news by category", description = "fetches all news by category entities and their data from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@GetMapping(value = "/{newsCategoryId}/news")
	public List<NewsCategoryDTO> getByCategories(@PathVariable(name = "newsCategoryId") Long newsCategoryId) {

		return newsService.getNewsByCategory(newsCategoryId)

				.stream()

				.map(newsCategoryEntity -> NewsCategoryDTO.builder()

						.id(newsCategoryEntity.getId())

						.title(newsCategoryEntity.getTitle())

						.description(newsCategoryEntity.getDescription())

						.build()

				)

				.toList();
	}

	@Operation(summary = "Create", description = "Create News Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@PostMapping
	public void create(@RequestBody NewsCategoryDTO newsCategoryDTO) {
		newsCategoryService.create(newsCategoryDTO);
	}

	@Operation(summary = "Update", description = "Update News Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@PutMapping(value = "/{newsCategoryId}")
	public NewsCategoryDTO update(@PathVariable(name = "newsCategoryId") Long newsCategoryId,
			@RequestBody NewsCategoryDTO newsDTO) {
		NewsCategoryEntity newsEntity = newsCategoryService.update(newsCategoryId, newsDTO);

		return NewsCategoryDTO

				.builder()

				.title(newsEntity.getTitle())

				.description(newsEntity.getDescription())

				.build();
	}

	@Operation(summary = "Delete", description = "Delete News Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@DeleteMapping(value = "/{newsCategoryId}")
	public void delete(@PathVariable(name = "newsCategoryId") Long newsCategoryId) {
		newsCategoryService.delete(newsCategoryId);
	}

}
