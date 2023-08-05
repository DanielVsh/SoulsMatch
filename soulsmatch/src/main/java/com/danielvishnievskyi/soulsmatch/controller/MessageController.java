package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.service.chat.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @GetMapping("/chat/{id}")
  public ResponseEntity<Page<MessageResponseDto>> getMessagesByChatId(
    @PathVariable(name = "id") Long chatId,
    Pageable pageable
  ) {
    return ResponseEntity.ok(messageService.getPageableMessagesByChatId(chatId, pageable));
  }
}
