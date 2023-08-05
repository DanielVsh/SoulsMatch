package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @GetMapping()
  public ResponseEntity<Page<ChatResponseDto>> findChatsByUsername(
    @AuthenticationPrincipal Soul soul,
    Pageable pageable
  ) {
    return ResponseEntity.ok(chatService.findChatsByUsername(soul.getUsername(), pageable));
  }

  @PostMapping("/create")
  public ResponseEntity<String> createChat(@RequestBody ChatRequestDto chatRequestDto) {
    chatService.createChat(chatRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body("Chat successfully created");
  }


}
