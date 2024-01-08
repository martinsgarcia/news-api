package com.news.api.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "News DTO")
public class NewsDTO {

	@Schema(description = "Unique news identifier")
	private int id;

	@Schema(description = "News title")
	private String title;

	@Schema(description = "News description")
	private String description;

}
