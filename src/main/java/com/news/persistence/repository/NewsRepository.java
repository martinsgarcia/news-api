package com.news.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.news.persistence.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>,
		EntityGraphQuerydslPredicateExecutor<NewsEntity>, EntityGraphJpaRepository<NewsEntity, Long> {

}
