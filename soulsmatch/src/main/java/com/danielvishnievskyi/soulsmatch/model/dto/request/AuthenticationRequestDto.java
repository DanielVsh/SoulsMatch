package com.danielvishnievskyi.soulsmatch.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
  @NotBlank(message = "Username is required")
  private String username;
  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 32, message = "Password is required and should contain 8 to 32 characters")
  private String password;
}
