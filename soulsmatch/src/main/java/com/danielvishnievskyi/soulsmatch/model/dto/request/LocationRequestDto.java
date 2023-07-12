package com.danielvishnievskyi.soulsmatch.model.dto.request;


public record LocationRequestDto(
  String name,
  double latitude,
  double longitude
) {
}
