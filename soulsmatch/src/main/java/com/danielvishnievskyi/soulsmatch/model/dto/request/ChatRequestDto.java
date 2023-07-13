package com.danielvishnievskyi.soulsmatch.model.dto.request;

import java.util.List;

public record ChatRequestDto(
  List<Long> participants
) {
}
