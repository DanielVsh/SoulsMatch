package com.danielvishnievskyi.soulsmatch.mapper.chat;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ChatRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ChatResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class ChatMapperServiceImpl implements ChatMapperService {

  @Autowired
  protected SoulRepository soulRepository;

  @Override
  @Mapping(source = "participants", target = "participants", qualifiedByName = "mapIdsToParticipants")
  public abstract Chat requestDtoToEntity(ChatRequestDto chatRequestDto);

  @Override
  public abstract ChatResponseDto entityToResponseDto(Chat chat);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "participants", target = "participants", qualifiedByName = "mapIdsToParticipants")
  public abstract void updateEntityByRequestDto(ChatRequestDto chatRequestDto, @MappingTarget Chat chat);

  @Named("mapIdsToParticipants")
  protected List<Soul> mapIdsToParticipants(List<Long> soulIds) {
    return soulRepository.findAllById(soulIds);
  }
}
