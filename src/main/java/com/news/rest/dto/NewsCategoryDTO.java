package com.news.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "News Category DTO")
public class NewsCategoryDTO {

	@Schema(description = "Unique category identifier")
	private Long id;

	@Schema(description = "Category title")
	private String title;

	@Schema(description = "Category description")
	private String description;

}
