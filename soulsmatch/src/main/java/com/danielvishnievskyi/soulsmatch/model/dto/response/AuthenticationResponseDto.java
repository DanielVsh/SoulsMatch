package com.danielvishnievskyi.soulsmatch.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponseDto(
  @JsonProperty("accessToken") String accessToken,
  @JsonProperty("refreshToken") String refreshToken
) {

}
