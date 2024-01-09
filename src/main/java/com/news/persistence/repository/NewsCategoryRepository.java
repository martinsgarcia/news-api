package com.news.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.persistence.entity.NewsCategoryEntity;

public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {

}
