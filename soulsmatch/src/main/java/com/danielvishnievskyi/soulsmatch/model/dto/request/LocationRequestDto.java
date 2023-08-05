package com.danielvishnievskyi.soulsmatch.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDto {
  @NotBlank(message = "Name is required")
  private String name;
  @NotNull(message = "Latitude is required")
  private Double latitude;
  @NotNull(message = "Longitude is required")
  private Double longitude;
}
