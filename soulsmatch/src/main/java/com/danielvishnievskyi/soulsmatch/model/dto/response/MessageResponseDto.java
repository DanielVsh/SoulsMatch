package com.danielvishnievskyi.soulsmatch.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
  private UUID id;
  private SoulResponseDto soul;
  private String content;
  private boolean isRead;
  private LocalDateTime time;
}
