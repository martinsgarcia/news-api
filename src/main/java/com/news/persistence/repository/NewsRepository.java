package com.news.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.persistence.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

}
