package com.danielvishnievskyi.soulsmatch.exception;

import com.danielvishnievskyi.soulsmatch.exception.city.CityNotFoundException;
import com.danielvishnievskyi.soulsmatch.exception.photo.PhotoNotFoundException;
import com.danielvishnievskyi.soulsmatch.exception.profile.ProfileAlreadyExistsException;
import com.danielvishnievskyi.soulsmatch.exception.soul.SoulAlreadyExistsException;
import com.danielvishnievskyi.soulsmatch.exception.soul.SoulNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandleController {

  @ExceptionHandler({
    CityNotFoundException.class,
    PhotoNotFoundException.class,
    SoulNotFoundException.class
  })
  @ResponseStatus(NOT_FOUND)
  public ExceptionResponse handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      NOT_FOUND.value(),
      NOT_FOUND.getReasonPhrase(),
      ex.getMessage(),
      request.getRequestURI(),
      request.getMethod()
    );
  }

  @ExceptionHandler({
    ProfileAlreadyExistsException.class,
    SoulAlreadyExistsException.class
  })
  @ResponseStatus(CONFLICT)
  public ExceptionResponse handleAlreadyExistsException(RuntimeException ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      CONFLICT.value(),
      CONFLICT.getReasonPhrase(),
      ex.getMessage(),
      request.getRequestURI(),
      request.getMethod()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
    System.out.println(Arrays.toString(ex.getStackTrace()));
    return new ExceptionResponse(
      LocalDateTime.now(),
      BAD_REQUEST.value(),
      BAD_REQUEST.getReasonPhrase(),
      Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
      request.getRequestURI(),
      request.getMethod()
    );
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionResponse handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      BAD_REQUEST.value(),
      BAD_REQUEST.getReasonPhrase(),
      ex.getMessage(),
      request.getRequestURI(),
      request.getMethod()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleException(Exception ex, HttpServletRequest request) {
    return new ExceptionResponse(
      LocalDateTime.now(),
      INTERNAL_SERVER_ERROR.value(),
      INTERNAL_SERVER_ERROR.getReasonPhrase(),
      ex.getMessage(),
      request.getRequestURI(),
      request.getMethod()
    );
  }
}
