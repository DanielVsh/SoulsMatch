package com.danielvishnievskyi.soulsmatch.model.dto.request;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;

import java.util.List;

public record ProfileRequestDto(
  String description,
  List<String> photos,
  List<Gender> preferredGenders
) {
}
