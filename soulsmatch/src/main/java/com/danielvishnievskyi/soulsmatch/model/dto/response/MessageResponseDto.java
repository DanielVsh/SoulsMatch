package com.danielvishnievskyi.soulsmatch.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
  private Long id;
  private SoulResponseDto soul;
  private String content;
  private boolean isRead;
  private LocalDateTime time;
}
