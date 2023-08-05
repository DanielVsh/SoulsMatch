package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDto {
  private String description;
  @Valid
  @NotEmpty(message = "Photos should not be empty")
  private List<PhotoRequestDto> photos;
  @NotNull(message = "Preferred genders should not be empty")
  @Size(min = 1, message = "Set at least one preferred gender")
  private List<Gender> preferredGenders;
  @Valid
  @NotNull(message = "Location should not be empty")
  private LocationRequestDto location;
}
