package com.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.persistence.entity.NewsCategoryEntity;
import com.news.persistence.repository.NewsCategoryRepository;
import com.news.rest.dto.NewsCategoryDTO;

@Service
public class NewsCategoryService {

	private final NewsCategoryRepository repository;

	public NewsCategoryService(NewsCategoryRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<NewsCategoryEntity> getAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public NewsCategoryEntity get(long categoryId) {
		return repository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category Id " + categoryId + " not found"));
	}

	@Transactional
	public void create(NewsCategoryDTO newsCategoryDTO) {

		repository.save(NewsCategoryEntity.builder()

				.title(newsCategoryDTO.getTitle())

				.description(newsCategoryDTO.getDescription())

				.build());
	}

	@Transactional
	public NewsCategoryEntity update(long categoryId, NewsCategoryDTO newsCategoryDTO) {

		NewsCategoryEntity newsCategoryEntity = repository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category Id" + categoryId + " not found to update"));

		newsCategoryEntity.setTitle(newsCategoryDTO.getTitle());
		newsCategoryEntity.setDescription(newsCategoryDTO.getDescription());

		repository.save(newsCategoryEntity);

		return newsCategoryEntity;
	}

	@Transactional
	public void delete(long categoryId) {

		NewsCategoryEntity newsCategoryEntity = repository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category Id " + categoryId + " not found to remove"));

		repository.delete(newsCategoryEntity);
	}

}
