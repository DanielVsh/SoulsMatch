package com.danielvishnievskyi.soulsmatch.service.chat;

import com.danielvishnievskyi.soulsmatch.mapper.chat.ChatMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatRepository chatRepository;
  private final ChatMapperService chatMapperService;

  @Override
  public ChatResponseDto createChat(ChatRequestDto chatRequestDto) {
    return chatMapperService.entityToResponseDto(
      chatRepository.save(
        chatMapperService.requestDtoToEntity(chatRequestDto)
      )
    );
  }
}
