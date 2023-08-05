package com.danielvishnievskyi.soulsmatch.mapper.chat.message;

import com.danielvishnievskyi.soulsmatch.mapper.chat.ChatMapperService;
import com.danielvishnievskyi.soulsmatch.mapper.soul.SoulMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Message;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = {SoulMapperService.class, ChatMapperService.class})
public abstract class MessageMapperServiceImpl implements MessageMapperService {

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "username", target = "soul")
  @Mapping(source = "chat", target = "chat")
  public abstract Message requestDtoToEntity(MessageRequestDto messageRequestDto);

  @Override
  public abstract MessageResponseDto entityToResponseDto(Message message);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "soul", ignore = true)
  @Mapping(target = "time", ignore = true)
  @Mapping(source = "chat", target = "chat")
  public abstract void updateEntityByRequestDto(MessageRequestDto messageRequestDto, @MappingTarget Message message);
}
