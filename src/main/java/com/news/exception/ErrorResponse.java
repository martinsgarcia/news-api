package com.news.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private String url;
	private String exception;
	private String cause;
	private String message;

}
