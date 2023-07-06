package com.danielvishnievskyi.soulsmatch.model.dto.response;

public record CityResponseDto(
  Long id,
  String name,
  double latitude,
  double longitude
) {
}
