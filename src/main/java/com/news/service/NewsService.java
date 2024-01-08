package com.news.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.news.api.rest.dto.NewsDTO;

@Service
public class NewsService {

	public List<NewsDTO> getNews() {

		return Arrays.asList(

				NewsDTO

						.builder()

						.id(1)

						.title("Notícia 1")

						.description("Descrição Notícia 1")

						.build()

		);

	}

}
