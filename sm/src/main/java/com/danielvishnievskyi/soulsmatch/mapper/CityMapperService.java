package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CityRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class CityMapperService implements MapperService<City, CityRequestDto, CityResponseDto> {

  @Override
  public abstract City requestDtoToEntity(CityRequestDto cityRequestDto);

  @Override
  public abstract CityResponseDto entityToResponseDto(City city);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  public abstract void updateEntityByRequestDto(CityRequestDto cityRequestDto, @MappingTarget City city);
}
