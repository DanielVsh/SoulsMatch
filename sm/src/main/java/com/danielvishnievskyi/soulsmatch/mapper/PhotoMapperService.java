package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class PhotoMapperService implements MapperService<Photo, PhotoRequestDto, PhotoResponseDto>{

  @Override
  public abstract Photo requestDtoToEntity(PhotoRequestDto photoRequestDto);

  @Override
  public abstract PhotoResponseDto entityToResponseDto(Photo photo);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  public abstract void updateEntityByRequestDto(PhotoRequestDto photoRequestDto, @MappingTarget Photo photo);
}
