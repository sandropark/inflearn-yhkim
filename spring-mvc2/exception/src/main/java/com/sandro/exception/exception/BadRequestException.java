package com.sandro.exception.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {
}
