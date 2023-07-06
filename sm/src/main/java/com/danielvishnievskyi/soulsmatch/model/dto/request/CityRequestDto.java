package com.danielvishnievskyi.soulsmatch.model.dto.request;

public record CityRequestDto(
  String name,
  double latitude,
  double longitude
) {
}
