package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import com.danielvishnievskyi.soulsmatch.validation.annotation.PasswordComplexity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
  @NotBlank(message = "Username should not be null")
  private String username;
  @NotBlank(message = "Password should not be null")
  @Size(min = 8, max = 32, message = "Password length must be 8 to 32")
  @PasswordComplexity
  private String password;
  @NotBlank(message = "Firstname should not be null")
  private String firstName;
  @NotBlank(message = "Lastname should not be null")
  private String lastName;
  @NotNull(message = "Gender should not be null")
  private Gender gender;
  @NotBlank
  @Size(min = 10, max = 10, message = "Date format is (yyyy-MM-dd)")
  private String birthDate;
}
