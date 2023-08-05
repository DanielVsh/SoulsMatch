package com.danielvishnievskyi.soulsmatch.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {
  private Long id;
  private String name;
  private double latitude;
  private double longitude;
}
