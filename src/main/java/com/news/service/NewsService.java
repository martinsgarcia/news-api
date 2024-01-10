package com.news.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.news.persistence.entity.NewsEntity;
import com.news.persistence.entity.QNewsEntity;
import com.news.persistence.repository.NewsRepository;
import com.news.rest.dto.CreationNewsDTO;
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
	public Page<NewsEntity> getAll(Pageable pageable) {
		return newsRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public NewsEntity get(long newsId) {
		return newsRepository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id " + newsId + " not found"));
	}

	@Transactional
	public void create(CreationNewsDTO creationNewsDTO) {

		newsRepository.save(NewsEntity.builder()

				.title(creationNewsDTO.getTitle())

				.description(creationNewsDTO.getDescription())

				.category(newsCategoryService.get(creationNewsDTO.getCategoryId()))

				.build());
	}

	@Transactional
	public NewsEntity update(long newsId, CreationNewsDTO creationNewsDTO) {

		NewsEntity newsEntity = newsRepository.findById(newsId)
				.orElseThrow(() -> new RuntimeException("News Id" + newsId + " not found to update"));

		newsEntity.setTitle(creationNewsDTO.getTitle());
		newsEntity.setDescription(creationNewsDTO.getDescription());
		newsEntity.setCategory(newsCategoryService.get(creationNewsDTO.getCategoryId()));

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
