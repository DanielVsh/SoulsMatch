package com.danielvishnievskyi.soulsmatch.exception.profile;

public class ProfileAlreadyExistsException extends RuntimeException{
  public ProfileAlreadyExistsException(String message) {
    super(message);
  }
}
