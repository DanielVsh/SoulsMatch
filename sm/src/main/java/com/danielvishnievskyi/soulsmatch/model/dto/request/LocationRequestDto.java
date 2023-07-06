package com.danielvishnievskyi.soulsmatch.model.dto.request;

public record LocationRequestDto(
  Long country,
  Long city,
  double latitude,
  double longitude
) {
}
