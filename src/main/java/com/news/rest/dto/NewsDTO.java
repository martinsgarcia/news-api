package com.news.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "News DTO")
public class NewsDTO {

	@Schema(description = "Unique news identifier")
	private Long id;

	@Schema(description = "News title")
	private String title;

	@Schema(description = "News description")
	private String description;

}
