package com.danielvishnievskyi.soulsmatch.service.chat;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;

public interface ChatService {
  ChatResponseDto createChat(ChatRequestDto chatRequestDto);
}
