package com.danielvishnievskyi.soulsmatch.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
  @NotNull(message = "Chat is required")
  private UUID chat;
  @NotBlank(message = "Username is required")
  private String username;
  @NotBlank(message = "Content should not be empty")
  private String content;
  private boolean isRead = false;
}
