package com.danielvishnievskyi.soulsmatch.model.dto.response;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public record SoulResponseDto(
  Long id,
  String email,
  String firstName,
  String lastName,
  @Enumerated(EnumType.STRING) Gender gender,
  int age,
  LocationResponseDto location,
  List<PhotoResponseDto> photos
) {
}
