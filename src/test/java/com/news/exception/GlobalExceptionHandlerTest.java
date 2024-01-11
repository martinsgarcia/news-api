package com.news.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

	@Test
	void httpServerException() throws IOException {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/example");

		HttpServerErrorException ex = mock(HttpServerErrorException.class, Mockito.RETURNS_DEEP_STUBS);

		Mockito.when(ex.getResponseBodyAsString())
				.thenReturn(new ObjectMapper().writeValueAsString(new HttpErrorResponse()));

		assertNotNull(globalExceptionHandler.httpServerException(ex));
	}

	@Test
	void httpClientException() throws IOException {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/example");

		HttpClientErrorException ex = mock(HttpClientErrorException.class, Mockito.RETURNS_DEEP_STUBS);

		Mockito.when(ex.getResponseBodyAsString())
				.thenReturn(new ObjectMapper().writeValueAsString(new HttpErrorResponse()));

		assertNotNull(globalExceptionHandler.httpClientException(ex));
	}

	@Test
	void handleException() {

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://example.com"));

		Exception ex = new Exception("Generic Exception");

		ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleException(ex, request);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

		ErrorResponse errorResponse = responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals("http://example.com", errorResponse.getUrl());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
		assertEquals("Exception", errorResponse.getException());
		assertEquals("Generic Exception", errorResponse.getMessage());
	}

	@Test
	void handleExceptionErrorCode402() {

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getAttribute("jakarta.servlet.error.status_code")).thenReturn(HttpStatus.PAYMENT_REQUIRED.value());

		when(request.getRequestURL()).thenReturn(new StringBuffer("http://example.com"));

		Exception ex = new Exception("Generic Exception");

		ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleException(ex, request);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.PAYMENT_REQUIRED, responseEntity.getStatusCode());

		ErrorResponse errorResponse = responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals("http://example.com", errorResponse.getUrl());
		assertEquals(HttpStatus.PAYMENT_REQUIRED.value(), errorResponse.getStatus());
		assertEquals("Exception", errorResponse.getException());
		assertEquals("Generic Exception", errorResponse.getMessage());
	}

	@Test
	void handleExceptionWithCause() {

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://example.com"));

		Exception ex = mock(Exception.class);
		when(ex.getCause()).thenReturn(new Throwable());
		when(ex.getMessage()).thenReturn("Http Error");

		ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleException(ex, request);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

		ErrorResponse errorResponse = responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals("http://example.com", errorResponse.getUrl());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
		assertEquals("Http Error", errorResponse.getMessage());
		assertEquals("Throwable", errorResponse.getCause());

	}

	@Test
	void methodArgumentNotValidException() {

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://example.com"));

		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, createBindingResult());

		ErrorResponse response = globalExceptionHandler.methodArgumentNotValidException(request, ex);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals("MethodArgumentNotValidException", response.getException());
	}

	@Test
	void constraintViolationException() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/example");

		ConstraintViolationException ex = mock(ConstraintViolationException.class, Mockito.RETURNS_DEEP_STUBS);
		Mockito.when(ex.getMessage()).thenReturn("Violation message");
		Mockito.when(ex.getConstraintViolations()).thenReturn(Collections.emptySet());

		assertNotNull(globalExceptionHandler.constraintViolationException(request, ex));
	}

	@Test
	void buildErrorResponse() {

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://example.com"));

		Exception ex = new Exception("Test Exception");

		ErrorResponse response = globalExceptionHandler.buildErrorResponse(request,
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);

		assertNotNull(response);
		assertEquals("http://example.com", response.getUrl());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
		assertEquals("Exception", response.getException());
		assertEquals("Test Exception", response.getMessage());
	}

	private BindingResult createBindingResult() {
		BindingResult result = mock(BindingResult.class);
		when(result.getFieldErrors()).thenReturn(Collections.singletonList(createFieldError()));
		return result;
	}

	private FieldError createFieldError() {
		return new FieldError("objectName", "fieldName", "defaultMessage");
	}
}
