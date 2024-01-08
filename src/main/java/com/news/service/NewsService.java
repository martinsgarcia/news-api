package com.news.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.api.rest.dto.NewsDTO;
import com.news.persistence.entity.NewsEntity;
import com.news.persistence.repository.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private NewsRepository repository;

	@Transactional(readOnly = true)
	public List<NewsEntity> getNews() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NewsEntity get(long newsId) {
		return repository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
	}

	@Transactional
	public void create(NewsDTO newsDTO) {

		repository.save(NewsEntity.builder()

				.title(newsDTO.getTitle())

				.description(newsDTO.getDescription())

				.build());
	}

	@Transactional
	public NewsEntity update(long newsId, NewsDTO newsDTO) {

		NewsEntity newsEntity = repository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));

		newsEntity.setTitle(newsDTO.getTitle());
		newsEntity.setDescription(newsDTO.getDescription());

		repository.save(newsEntity);

		return newsEntity;
	}

	@Transactional
	public void delete(long newsId) {

		NewsEntity newsEntity = repository.findById(newsId).orElseThrow(() -> new RuntimeException("News not found"));
		repository.delete(newsEntity);
	}

}
