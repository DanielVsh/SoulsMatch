package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;

import java.util.List;

public record SoulRequestDto(
  String email,
  String password,
  String firstName,
  String lastName,
  Gender gender,
  String birthDate,
  Long location,
  List<String> photos
) {
}
