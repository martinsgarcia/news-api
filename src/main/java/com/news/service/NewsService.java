package com.news.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.persistence.entity.NewsEntity;
import com.news.persistence.repository.NewsRepository;
import com.news.rest.dto.NewsDTO;

@Service
public class NewsService {

	private final NewsRepository repository;
	private final NewsCategoryService newsCategoryService;

	public NewsService(NewsRepository repository, NewsCategoryService newsCategoryService) {
		this.repository = repository;
		this.newsCategoryService = newsCategoryService;
	}

	@Transactional(readOnly = true)
	public List<NewsEntity> getAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NewsEntity get(long newsId) {
		return repository.findById(newsId).orElseThrow(() -> new RuntimeException("News Id " + newsId + " not found"));
	}

	@Transactional
	public void create(NewsDTO newsDTO) {

		repository.save(NewsEntity.builder()

				.title(newsDTO.getTitle())

				.description(newsDTO.getDescription())

				.category(newsCategoryService.get(newsDTO.getCategoryId()))

				.build());
	}

	@Transactional
	public NewsEntity update(long newsId, NewsDTO newsDTO) {

		NewsEntity newsEntity = repository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id" + newsId + " not found to update"));

		newsEntity.setTitle(newsDTO.getTitle());
		newsEntity.setDescription(newsDTO.getDescription());
		newsEntity.setCategory(newsCategoryService.get(newsDTO.getCategoryId()));

		repository.save(newsEntity);

		return newsEntity;
	}

	@Transactional
	public void delete(long newsId) {

		NewsEntity newsEntity = repository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id " + newsId + " not found to remove"));
		repository.delete(newsEntity);
	}

	@Transactional(readOnly = true)
	public List<NewsEntity> getNewsByCategory(long categoryId) {

		return Collections.emptyList();
	}

}
