package com.danielvishnievskyi.soulsmatch.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandleController {

  @ExceptionHandler({
    CityNotFoundException.class
  })
  @ResponseStatus(NOT_FOUND)
  public ExceptionResponse handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      NOT_FOUND.value(),
      NOT_FOUND.toString(),
      ex.getMessage(),
      request.getRequestURI()
    );
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleException(Exception ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      INTERNAL_SERVER_ERROR.value(),
      INTERNAL_SERVER_ERROR.toString(),
      ex.getMessage(),
      request.getRequestURI()
    );
  }
}
