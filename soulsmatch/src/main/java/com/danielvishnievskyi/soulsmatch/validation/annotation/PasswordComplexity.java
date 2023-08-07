package com.danielvishnievskyi.soulsmatch.validation.annotation;

import com.danielvishnievskyi.soulsmatch.validation.validator.PasswordComplexityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordComplexityValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordComplexity {
  String message() default "Invalid password. It should contain at least one uppercase letter, one lowercase letter, one digit, and one symbol.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
