package com.news.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.news.persistence.entity.NewsEntity;
import com.news.persistence.entity.QNewsEntity;
import com.news.persistence.repository.NewsRepository;
import com.news.rest.dto.NewsDTO;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class NewsService {

	private final NewsRepository newsRepository;
	private final NewsCategoryService newsCategoryService;

	public NewsService(NewsRepository newsRepository, NewsCategoryService newsCategoryService) {
		this.newsRepository = newsRepository;
		this.newsCategoryService = newsCategoryService;
	}

	@Transactional(readOnly = true)
	public List<NewsEntity> getAll() {
		return newsRepository.findAll();
	}

	@Transactional(readOnly = true)
	public NewsEntity get(long newsId) {
		return newsRepository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id " + newsId + " not found"));
	}

	@Transactional
	public void create(NewsDTO newsDTO) {

		newsRepository.save(NewsEntity.builder()

				.title(newsDTO.getTitle())

				.description(newsDTO.getDescription())

				.category(newsCategoryService.get(newsDTO.getCategoryId()))

				.build());
	}

	@Transactional
	public NewsEntity update(long newsId, NewsDTO newsDTO) {

		NewsEntity newsEntity = newsRepository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id" + newsId + " not found to update"));

		newsEntity.setTitle(newsDTO.getTitle());
		newsEntity.setDescription(newsDTO.getDescription());
		newsEntity.setCategory(newsCategoryService.get(newsDTO.getCategoryId()));

		newsRepository.save(newsEntity);

		return newsEntity;
	}

	@Transactional
	public void delete(long newsId) {

		NewsEntity newsEntity = newsRepository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id " + newsId + " not found to remove"));
		newsRepository.delete(newsEntity);
	}

	@Transactional(readOnly = true)
	public List<NewsEntity> getNewsByCategory(long categoryId) {

		QNewsEntity qNewsEntity = QNewsEntity.newsEntity;
		BooleanExpression expression = qNewsEntity.category.id.eq(categoryId);

		return (List<NewsEntity>) newsRepository.findAll(expression,
				DynamicEntityGraph.loading(Arrays.asList("category")));
	}

}
