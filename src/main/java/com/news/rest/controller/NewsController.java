package com.news.rest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.persistence.entity.NewsEntity;
import com.news.rest.dto.CreationNewsDTO;
import com.news.rest.dto.NewsDTO;
import com.news.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "News API", description = "The News API")
@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

	private final NewsService service;

	public NewsController(NewsService service) {
		this.service = service;
	}

	@Operation(summary = "Fetch all news", description = "fetches all news entities and their data from data source")
	@GetMapping
	public Page<NewsDTO> getAll(Pageable pageable) {

		Page<NewsEntity> newsPage = service.getAll(pageable);

		List<NewsDTO> newsDTO = newsPage.getContent().stream()

				.map(newsEntity -> NewsDTO.builder()

						.id(newsEntity.getId())

						.title(newsEntity.getTitle())

						.description(newsEntity.getDescription())

						.build()

				)

				.toList();

		return new PageImpl<>(newsDTO, newsPage.getPageable(), newsPage.getTotalElements());
	}

	@Operation(summary = "Get News", description = "Get News")
	@GetMapping(value = "/{newsId}")
	public NewsDTO get(@PathVariable(name = "newsId") Long newsId) {

		NewsEntity newsEntity = service.get(newsId);

		return NewsDTO

				.builder()

				.id(newsEntity.getId())

				.title(newsEntity.getTitle())

				.description(newsEntity.getDescription())

				.build();
	}

	@Operation(summary = "Create", description = "Create News")
	@PostMapping
	public void create(@Valid @RequestBody CreationNewsDTO creationNewsDTO) {
		service.create(creationNewsDTO);
	}

	@Operation(summary = "Update", description = "Update News")
	@PutMapping(value = "/{newsId}")
	public NewsDTO update(@PathVariable(name = "newsId") Long newsId,
			@Valid @RequestBody CreationNewsDTO creationNewsDTO) {
		NewsEntity newsEntity = service.update(newsId, creationNewsDTO);

		return NewsDTO

				.builder()

				.title(newsEntity.getTitle())

				.description(newsEntity.getDescription())

				.build();
	}

	@Operation(summary = "Delete", description = "Delete News")
	@DeleteMapping(value = "/{newsId}")
	public void delete(@PathVariable(name = "newsId") Long newsId) {
		service.delete(newsId);
	}

}
