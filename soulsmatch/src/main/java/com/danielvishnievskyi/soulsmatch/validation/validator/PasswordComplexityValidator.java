package com.danielvishnievskyi.soulsmatch.validation.validator;

import com.danielvishnievskyi.soulsmatch.validation.annotation.PasswordComplexity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordComplexityValidator implements ConstraintValidator<PasswordComplexity, String> {
  @Override
  public void initialize(PasswordComplexity constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // at least one uppercase letter
    if (!value.matches(".*[A-Z].*")) {
      return false;
    }

    // at least one lowercase letter
    if (!value.matches(".*[a-z].*")) {
      return false;
    }

    // at least one digit
    if (!value.matches(".*\\d.*")) {
      return false;
    }

    // at least one symbol
    if (!value.matches(".*[^a-zA-Z\\d].*")) {
      return false;
    }

    return true;
  }
}