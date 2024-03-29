package com.news.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "News Category DTO")
public class NewsCategoryDTO {

	@Schema(description = "Unique category identifier")
	private Long id;

	@NotNull
	@Schema(description = "Category title")
	private String title;

	@NotNull
	@Schema(description = "Category description")
	private String description;

}
