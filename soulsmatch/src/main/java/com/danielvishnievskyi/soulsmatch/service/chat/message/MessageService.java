package com.danielvishnievskyi.soulsmatch.service.chat.message;

import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
  MessageResponseDto createMessage(MessageRequestDto messageRequestDto);
  MessageResponseDto updateMessage(Long messageId, MessageRequestDto messageRequestDto);
  void deleteMessage(Long id);
  Page<MessageResponseDto> getPageableMessagesByChatId(Long chatId, Pageable pageable);
}
