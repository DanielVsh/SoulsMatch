package com.danielvishnievskyi.soulsmatch.model.dto.request;

public record AuthenticationRequestDto(
  String username,
  String password
) {
}
