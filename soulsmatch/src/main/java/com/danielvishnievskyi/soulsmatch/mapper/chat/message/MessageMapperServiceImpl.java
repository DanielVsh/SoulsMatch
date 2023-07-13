package com.danielvishnievskyi.soulsmatch.mapper.chat.message;

import com.danielvishnievskyi.soulsmatch.model.dto.request.MessageRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.MessageResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Message;
import com.danielvishnievskyi.soulsmatch.repository.ChatRepository;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.util.auth.CurrentUserProvider;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class MessageMapperServiceImpl implements MessageMapperService {

  @Autowired
  protected ChatRepository chatRepository;

  @Autowired
  protected SoulRepository soulRepository;

  @Autowired
  protected CurrentUserProvider currentUserProvider;

  @Override
  @Mapping(source = "chat", target = "chat", qualifiedByName = "mapLongToChat")
  @Mapping(target = "soul", expression = "java(soulRepository.findByEmail(currentUserProvider.getCurrentUsername()).orElseThrow())")
  //TODO: custom exception
  public abstract Message requestDtoToEntity(MessageRequestDto messageRequestDto);

  @Override
  public abstract MessageResponseDto entityToResponseDto(Message message);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "chat", target = "chat", qualifiedByName = "mapLongToChat")
  public abstract void updateEntityByRequestDto(MessageRequestDto messageRequestDto, @MappingTarget Message message);

  @Named("mapLongToChat")
  protected Chat mapLongToChat(Long id) {
    return chatRepository.findById(id)
      .orElseThrow();//TODO: custom exception
  }

}
