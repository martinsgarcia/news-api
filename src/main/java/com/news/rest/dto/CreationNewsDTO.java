package com.news.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Create/Update News DTO")
public class CreationNewsDTO {

	@Schema(description = "News title")
	private String title;

	@Schema(description = "News description")
	private String description;

	@Schema(description = "News category id")
	private Long categoryId;

}
