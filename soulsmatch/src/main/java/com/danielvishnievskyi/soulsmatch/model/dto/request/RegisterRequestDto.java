package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;

public record RegisterRequestDto(
  String username,
  String password,
  String firstName,
  String lastName,
  Gender gender,
  String birthDate
) {
}
