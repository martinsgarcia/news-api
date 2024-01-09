package com.news.exception;

import lombok.Data;

@Data
public class HttpServerError {

	private String timestamp;
	private Integer status;
	private String error;
	private String exception;
	private String message;
	private String path;

}
