package com.news.exception;

import lombok.Data;

@Data
public class HttpClientError {

	private String url;
	private String cause;
	private String timestamp;
	private Integer status;
	private String error;
	private String exception;
	private String message;
	private String path;
}
