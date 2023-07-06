package com.danielvishnievskyi.soulsmatch.model.dto.response;

import java.util.List;

public record CountryResponseDto(
  Long id,
  String name,
  List<CityResponseDto> cities
) {
}
