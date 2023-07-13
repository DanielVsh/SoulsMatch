package com.danielvishnievskyi.soulsmatch.model.dto.response;

import java.util.List;

public record ChatResponseDto(
  Long id,
  List<SoulResponseDto> participants,
  List<MessageResponseDto> messages
) {
}
