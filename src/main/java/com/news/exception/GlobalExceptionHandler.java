package com.news.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	private ObjectMapper mapper = new ObjectMapper();

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpServerErrorException.class)
	@ResponseBody
	public HttpErrorResponse httpServerException(HttpServerErrorException ex) throws IOException {

		log.error("HTTP Server Error: ", ex);

		return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorResponse.class);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public HttpErrorResponse httpClientException(HttpClientErrorException ex) throws IOException {

		log.error("HTTP Client Error: ", ex);

		return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorResponse.class);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
		HttpStatus status = getStatusFromRequest(req);

		return new ResponseEntity<>(this.buildErrorResponse(req, status.value(), ex), status);
	}

	private HttpStatus getStatusFromRequest(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

		if (statusCode != null) {
			return HttpStatus.valueOf(statusCode);
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {

		log.error("MethodArgumentNotValidException Error: ", ex);

		BindingResult result = ex.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();

		if (fieldErrors.isEmpty())
			return null;

		return this.buildErrorResponse(req, HttpStatus.BAD_REQUEST.value(), ex, fieldErrors.get(0).getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorResponse constraintViolationException(HttpServletRequest req, ConstraintViolationException ex) {

		log.error("ConstraintViolationException Error: ", ex);

		Integer statusCode = HttpStatus.BAD_REQUEST.value();

		if (ex.getConstraintViolations().isEmpty())
			return this.buildErrorResponse(req, statusCode, ex);

		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> constraint : ex.getConstraintViolations()) {

			if (constraint.getConstraintDescriptor().getAnnotation() instanceof NotEmpty)
				errors.add(0, constraint.getMessage());
			else
				errors.add(constraint.getMessage());
		}

		return this.buildErrorResponse(req, statusCode, ex, errors.get(0));
	}

	private ErrorResponse buildErrorResponse(HttpServletRequest req, Integer status, Exception ex) {
		return this.buildErrorResponse(req, status, ex, ex.getMessage());
	}

	private ErrorResponse buildErrorResponse(HttpServletRequest req, Integer status, Exception ex, String message) {

		return ErrorResponse

				.builder()

				.url(req.getRequestURL().toString())

				.status(status)

				.exception(ex.getClass().getSimpleName())

				.message(message)

				.build();
	}
}
