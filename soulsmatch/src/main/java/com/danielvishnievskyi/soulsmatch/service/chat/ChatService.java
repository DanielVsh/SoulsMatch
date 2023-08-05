package com.danielvishnievskyi.soulsmatch.service.chat;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
  void createChat(ChatRequestDto chatRequestDto);
  Page<ChatResponseDto> findChatsByUsername(String username, Pageable pageable);
}
