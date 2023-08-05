package com.danielvishnievskyi.soulsmatch.mapper.profile;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class ProfileMapperServiceImpl implements ProfileMapperService {

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "soul", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "photos", ignore = true)
  public abstract Profile requestDtoToEntity(ProfileRequestDto profileRequestDto);

  @Override
  public abstract ProfileResponseDto entityToResponseDto(Profile profile);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "soul", ignore = true)
  @Mapping(target = "location", ignore = true)
  @Mapping(target = "photos", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  public abstract void updateEntityByRequestDto(ProfileRequestDto profileRequestDto, @MappingTarget Profile profile);
}
