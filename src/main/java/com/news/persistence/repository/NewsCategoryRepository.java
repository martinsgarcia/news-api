package com.news.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.news.persistence.entity.NewsCategoryEntity;

public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long>,
		EntityGraphQuerydslPredicateExecutor<NewsCategoryEntity>, EntityGraphJpaRepository<NewsCategoryEntity, Long> {

}
