package com.danielvishnievskyi.soulsmatch.model.dto.request;

import java.util.List;

public record CountryRequestDto(
  String name,
  List<Long> cities
) {
}
