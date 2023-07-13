package com.danielvishnievskyi.soulsmatch.mapper.chat.message;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Message;

public interface MessageMapperService extends MapperService<Message, MessageRequestDto, MessageResponseDto> {
}
