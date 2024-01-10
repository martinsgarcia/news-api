package com.news.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Exception thrown when there is an unexpected error by the server")
public class ErrorResponse {

	@Schema(description = "Request URL")
	private String url;

	@Schema(description = "Error code")
	private Integer status;

	@Schema(description = "Error class")
	private String exception;

	@Schema(description = "Error cause")
	private String cause;

	@Schema(description = "Error message")
	private String message;

}
