package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.model.entity.Country;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.repository.CityRepository;
import com.danielvishnievskyi.soulsmatch.repository.CountryRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class LocationMapperService implements MapperService<Location, LocationRequestDto, LocationResponseDto> {

  @Autowired
  protected CountryRepository countryRepository;

  @Autowired
  protected CityRepository cityRepository;

  @Autowired
  protected CountryMapperService countryMapperService;

  @Autowired
  protected CityMapperService cityMapperService;

  @Override
  @Mapping(source = "country", target = "country", qualifiedByName = "mapDtoToCountry")
  @Mapping(source = "city", target = "city", qualifiedByName = "mapDtoToCity")
  public abstract Location requestDtoToEntity(LocationRequestDto locationRequestDto);

  @Override
  @Mapping(source = "country", target = "country", qualifiedByName = "mapCountryToDto")
  @Mapping(source = "city", target = "city", qualifiedByName = "mapCityToDto")
  public abstract LocationResponseDto entityToResponseDto(Location location);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "country", target = "country", qualifiedByName = "mapDtoToCountry")
  @Mapping(source = "city", target = "city", qualifiedByName = "mapDtoToCity")
  public abstract void updateEntityByRequestDto(LocationRequestDto locationRequestDto, @MappingTarget Location location);

  @Named("mapDtoToCountry")
  protected Country mapDtoToCountry(Long id) {
    //TODO: custom exception
    return countryRepository.findById(id).orElseThrow();
  }

  @Named("mapDtoToCity")
  protected City mapDtoToCity(Long id) {
    //TODO: custom exception
    return cityRepository.findById(id).orElseThrow();
  }

  @Named("mapCountryToDto")
  protected CountryResponseDto mapCountryToDto(Country country) {
    return countryMapperService.entityToResponseDto(country);
  }

  @Named("mapCityToDto")
  protected CityResponseDto mapCityToDto(City city) {
    return cityMapperService.entityToResponseDto(city);
  }
}
