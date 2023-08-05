package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoulRequestDto {
  private String username;
  @Size(min = 8, message = "Password should have at least 8 symbols") @Null
  private String password;
  private String firstName;
  private String lastName;
  private Gender gender;
  private String birthDate;
  @Valid
  private LocationRequestDto location;
  @Valid
  private List<PhotoRequestDto> photos;
}
