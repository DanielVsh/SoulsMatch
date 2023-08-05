package com.danielvishnievskyi.soulsmatch.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {
  private Long id;
  private List<SoulResponseDto> participants;
}
