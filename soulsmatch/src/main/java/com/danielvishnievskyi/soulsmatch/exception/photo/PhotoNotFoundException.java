package com.danielvishnievskyi.soulsmatch.exception.photo;

public class PhotoNotFoundException extends RuntimeException{
  public PhotoNotFoundException(String message) {
    super(message);
  }
}
