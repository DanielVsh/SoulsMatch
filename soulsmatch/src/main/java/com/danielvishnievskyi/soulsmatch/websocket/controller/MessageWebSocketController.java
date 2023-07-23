package com.danielvishnievskyi.soulsmatch.websocket.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.service.chat.message.MessageService;
import lombok.RequiredArgsConstructor;
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
}
