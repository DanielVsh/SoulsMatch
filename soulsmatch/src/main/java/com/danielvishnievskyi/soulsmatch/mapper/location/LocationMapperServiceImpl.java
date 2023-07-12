package com.danielvishnievskyi.soulsmatch.mapper.location;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class LocationMapperServiceImpl implements MapperService<Location, LocationRequestDto, LocationResponseDto> {

  @Override
  public abstract Location requestDtoToEntity(LocationRequestDto locationRequestDto);

  @Override
  public abstract LocationResponseDto entityToResponseDto(Location location);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  public abstract void updateEntityByRequestDto(LocationRequestDto locationRequestDto, @MappingTarget Location location);
}
