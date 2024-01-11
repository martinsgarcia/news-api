package com.news.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Exception thrown when an HTTP 4xx or 5xx is received.")
public class HttpErrorResponse {

	@Schema(description = "Request URL")
	private String url;

	@Schema(description = "Error cause")
	private String cause;

	@Schema(description = "Request timestamp")
	private String timestamp;

	@Schema(description = "Error code")
	private Integer status;

	@Schema(description = "Request error")
	private String error;

	@Schema(description = "Error class")
	private String exception;

	@Schema(description = "Error message")
	private String message;

	@Schema(description = "Error path")
	private String path;
}
