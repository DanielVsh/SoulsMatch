package com.danielvishnievskyi.soulsmatch.model.dto.response;


public record LocationResponseDto(
  Long id,
  String name,
  double latitude,
  double longitude
) {
}
