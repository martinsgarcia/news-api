package com.news.api.rest.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewsDTO {

	private int id;
	private String title;
	private String description;

}
