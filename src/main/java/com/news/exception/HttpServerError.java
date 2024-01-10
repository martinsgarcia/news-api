package com.news.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Exception thrown when an HTTP 5xx is received")
public class HttpServerError {

	@Schema(description = "Request timestamp")
	private String timestamp;

	@Schema(description = "Error code")
	private Integer status;

	@Schema(description = "Request error")
	private String error;

	@Schema(description = "Error class")
	private String exception;

	@Schema(description = "Request URL")
	private String message;

	@Schema(description = "Error path")
	private String path;
}
