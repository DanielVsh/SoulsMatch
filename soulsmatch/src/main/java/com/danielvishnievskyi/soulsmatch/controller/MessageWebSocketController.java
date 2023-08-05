package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.service.chat.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

  private final MessageService messageService;

  @MessageMapping("/chat/{id}/message")
  @SendTo("/topic/chat/{id}/messages")
  public MessageResponseDto sendMessage(MessageRequestDto message) {
    return messageService.createMessage(message);
  }

  @MessageMapping("/chat/{id}/message/{messageId}")
  @SendTo("/topic/chat/{id}/messages")
  public MessageResponseDto updateMessage(
    @DestinationVariable(value = "messageId") Long id,
    MessageRequestDto message
  ) {
    return messageService.updateMessage(id, message);
  }
}
