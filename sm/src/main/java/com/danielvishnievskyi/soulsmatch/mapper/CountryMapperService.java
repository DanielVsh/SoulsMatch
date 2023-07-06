package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CountryRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.model.entity.Country;
import com.danielvishnievskyi.soulsmatch.repository.CityRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class CountryMapperService implements MapperService<Country, CountryRequestDto, CountryResponseDto> {

  @Autowired
  protected CityRepository cityRepository;

  @Autowired
  protected CityMapperService cityMapperService;

  @Override
  @Mapping(source = "cities", target = "cities", qualifiedByName = "mapDtoToCities")
  public abstract Country requestDtoToEntity(CountryRequestDto countryRequestDto);

  @Override
  @Mapping(source = "cities", target = "cities", qualifiedByName = "mapCitiesToDto")
  public abstract CountryResponseDto entityToResponseDto(Country country);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "cities", target = "cities", qualifiedByName = "mapDtoToCities")
  public abstract void updateEntityByRequestDto(CountryRequestDto countryRequestDto, @MappingTarget Country country);

  @Named("mapDtoToCities")
  protected List<City> mapDtoToCities(List<Long> cityIds) {
    //TODO: throw the exception
    return cityRepository.findAllById(cityIds);
  }

  @Named("mapCitiesToDto")
  protected List<CityResponseDto> mapCitiesToDto(List<City> cities) {
    return cities.stream()
      .map(city -> cityMapperService.entityToResponseDto(city))
      .toList();
  }
}
