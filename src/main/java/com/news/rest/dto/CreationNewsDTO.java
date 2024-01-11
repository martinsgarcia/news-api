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
@Schema(description = "Create/Update News DTO")
public class CreationNewsDTO {

	@NotNull
	@Schema(description = "News title")
	private String title;

	@NotNull
	@Schema(description = "News description")
	private String description;

	@NotNull
	@Schema(description = "News category id")
	private Long categoryId;

}
