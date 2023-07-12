package com.danielvishnievskyi.soulsmatch.model.dto.response;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public record ProfileResponseDto(
  Long id,
  SoulResponseDto soul,
  String description,
  LocationResponseDto location,
  @Enumerated(EnumType.STRING) List<Gender> preferredGenders,
  List<PhotoResponseDto> photos
) {
}
