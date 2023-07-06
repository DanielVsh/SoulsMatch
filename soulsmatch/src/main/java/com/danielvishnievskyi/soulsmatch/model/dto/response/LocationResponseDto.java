package com.danielvishnievskyi.soulsmatch.model.dto.response;

public record LocationResponseDto(
  Long id,
  CountryResponseDto country,
  CityResponseDto city,
  double latitude,
  double longitude
) {
}
