package com.danielvishnievskyi.soulsmatch.model.dto.response;

import java.time.LocalDateTime;

public record MessageResponseDto(
  Long id,
  String content,
  LocalDateTime time
) {
}
