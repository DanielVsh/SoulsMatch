package com.danielvishnievskyi.soulsmatch.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {
  private UUID id;
  private List<SoulResponseDto> participants;
}
