package com.news.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.api.rest.dto.NewsDTO;
import com.news.service.NewsService;

@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

	@Autowired
	private NewsService service;

	@GetMapping
	public List<NewsDTO> get() {
		return this.service.getNews();
	}

}
