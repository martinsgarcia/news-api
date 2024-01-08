package com.news.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.api.rest.dto.NewsDTO;
import com.news.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "News", description = "The News Api")
@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

	@Autowired
	private NewsService service;

	@Operation(summary = "Fetch all news", description = "fetches all news entities and their data from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@GetMapping
	public List<NewsDTO> getAll() {
		return this.service.getNews();
	}

	@Operation(summary = "Get News", description = "Get News")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@GetMapping(value = "/{id}")
	public NewsDTO get(@PathVariable(name = "id") Long id) {
		return null;
	}

	@Operation(summary = "Create", description = "Create News")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@PostMapping
	public void create(@RequestBody NewsDTO newsDTO) {
	}

	@Operation(summary = "Update", description = "Update News")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@PutMapping(value = "/{id}")
	public NewsDTO update(@PathVariable(name = "id") Long id, @RequestBody NewsDTO newsDTO) {
		return null;
	}

	@Operation(summary = "Delete", description = "Delete News")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
	@DeleteMapping(value = "/{id}")
	public NewsDTO delete(@PathVariable(name = "id") Long id) {
		return null;
	}

}
