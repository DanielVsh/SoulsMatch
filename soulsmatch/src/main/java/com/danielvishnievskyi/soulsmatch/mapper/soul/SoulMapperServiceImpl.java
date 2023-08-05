package com.danielvishnievskyi.soulsmatch.mapper.soul;

import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class SoulMapperServiceImpl implements SoulMapperService {

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected SoulRepository soulRepository;

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
  @Mapping(target = "password", expression = "java(passwordEncoder.encode(soulRequestDto.getPassword()))")
  public abstract Soul requestDtoToEntity(SoulRequestDto soulRequestDto);

  @Override
  public abstract SoulResponseDto entityToResponseDto(Soul soul);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
  @Mapping(target = "password", expression = "java(passwordEncoder.encode(soulRequestDto.getPassword()))")
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  public abstract void updateEntityByRequestDto(SoulRequestDto soulRequestDto, @MappingTarget Soul soul);

  @Override
  public Soul mapUsernameToSoul(String username) {
    return soulRepository.findByUsername(username)
      .orElseThrow(); //TODO custom exception
  }

  @Override
  public List<Soul> mapUsernamesToSouls(List<String> usernames) {
    return soulRepository.findAllByUsernameIn(usernames);
  }
}
