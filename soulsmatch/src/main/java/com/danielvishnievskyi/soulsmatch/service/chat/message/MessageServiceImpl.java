package com.danielvishnievskyi.soulsmatch.service.chat.message;

import com.danielvishnievskyi.soulsmatch.mapper.chat.message.MessageMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Message;
import com.danielvishnievskyi.soulsmatch.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
  private final MessageRepository messageRepository;
  private final MessageMapperService messageMapperService;

  @Override
  public MessageResponseDto createMessage(MessageRequestDto messageRequestDto) {
    Message message = messageMapperService.requestDtoToEntity(messageRequestDto);
    message.setTime(LocalDateTime.now());
    message.setRead(false);
    return messageMapperService.entityToResponseDto(messageRepository.save(message));
  }

  @Override
  public MessageResponseDto updateMessage(Long id, MessageRequestDto messageRequestDto) {
    Message message = messageRepository.findById(id).orElseThrow(); //TODO: custom exception
    messageMapperService.updateEntityByRequestDto(messageRequestDto, message);
    return messageMapperService.entityToResponseDto(messageRepository.save(message));
  }

  @Override
  public void deleteMessage(Long id) {
    messageRepository.deleteById(id);
  }

  @Override
  public Page<MessageResponseDto> getPageableMessagesByChatId(Long chatId, Pageable pageable) {
    return messageRepository.findAllByChatId(chatId, pageable)
      .map(messageMapperService::entityToResponseDto);
  }
}
