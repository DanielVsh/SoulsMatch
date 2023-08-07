package com.danielvishnievskyi.soulsmatch.mapper.chat;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;

import java.util.UUID;

public interface ChatMapperService extends MapperService<Chat, ChatRequestDto, ChatResponseDto> {
  Chat mapIdToChat(UUID id);
}
