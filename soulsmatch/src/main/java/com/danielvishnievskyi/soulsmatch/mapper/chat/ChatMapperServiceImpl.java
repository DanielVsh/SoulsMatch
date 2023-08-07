package com.danielvishnievskyi.soulsmatch.mapper.chat;

import com.danielvishnievskyi.soulsmatch.mapper.soul.SoulMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;
import com.danielvishnievskyi.soulsmatch.repository.ChatRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = {SoulMapperService.class})
public abstract class ChatMapperServiceImpl implements ChatMapperService {

  @Autowired
  protected ChatRepository chatRepository;

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "participants", target = "participants")
  public abstract Chat requestDtoToEntity(ChatRequestDto chatRequestDto);

  @Override
  public abstract ChatResponseDto entityToResponseDto(Chat chat);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "participants", target = "participants")
  public abstract void updateEntityByRequestDto(ChatRequestDto chatRequestDto, @MappingTarget Chat chat);

  @Override
  public Chat mapIdToChat(UUID id) {
    return chatRepository.findById(id)
      .orElseThrow();//TODO: custom exception
  }
}
