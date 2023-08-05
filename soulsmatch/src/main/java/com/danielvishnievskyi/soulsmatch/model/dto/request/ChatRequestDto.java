package com.danielvishnievskyi.soulsmatch.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
  @NotEmpty(message = "Chat should not be empty")
  @Size(min = 2, message = "Chat should have at least 2 participants")
  private List<String> participants;
}
