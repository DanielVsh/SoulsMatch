package com.danielvishnievskyi.soulsmatch.service.chat;

import com.danielvishnievskyi.soulsmatch.mapper.chat.ChatMapperService;
import com.danielvishnievskyi.soulsmatch.mapper.chat.ChatMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;
import com.danielvishnievskyi.soulsmatch.repository.ChatRepository;
import com.danielvishnievskyi.soulsmatch.repository.specification.ChatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatRepository chatRepository;
  private final ChatMapperService chatMapperService;
  private final ChatMapperServiceImpl chatMapperServiceImpl;

  @Override
  public Page<ChatResponseDto> findChatsByUsername(String username, Pageable pageable) {
    Specification<Chat> specification = Specification.where(ChatSpecification.findAllBySoulIn(username));
    return chatRepository.findAll(specification, pageable)
      .map(chatMapperServiceImpl::entityToResponseDto);
  }

  @Override
  public void createChat(ChatRequestDto chatRequestDto) {
    chatRepository.save(chatMapperService.requestDtoToEntity(chatRequestDto));
  }
}
