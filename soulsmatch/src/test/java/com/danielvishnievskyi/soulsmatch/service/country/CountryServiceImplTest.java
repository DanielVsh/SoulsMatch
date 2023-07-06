package com.danielvishnievskyi.soulsmatch.service.country;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CountryRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.model.entity.Country;
import com.danielvishnievskyi.soulsmatch.mapper.CountryMapperService;
import com.danielvishnievskyi.soulsmatch.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private CountryMapperService countryMapperService;

  @InjectMocks
  private CountryServiceImpl countryService;

  private Country country;
  private CountryResponseDto expectedResponseDto;
  private CountryRequestDto requestEntityDTO;

  @BeforeEach
  void beforeEach() {
    City city1 = new City(1L, "TestCity1", 1, 2);
    City city2 = new City(2L, "TestCity2", 5, 6);

    country = new Country(1L, "TestCountry", List.of(city1, city2));

    expectedResponseDto = new CountryResponseDto(
      1L,
      "TestCountry",
      List.of(
        new CityResponseDto(1L, "TestCity1", 1, 2),
        new CityResponseDto(2L, "TestCity2", 5, 6)
      ));

    requestEntityDTO = new CountryRequestDto("TestCountry", List.of(1L, 2L));
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    Long id = 1L;

    when(countryRepository.findById(id)).thenReturn(Optional.of(country));
    when(countryMapperService.entityToResponseDto(country)).thenReturn(expectedResponseDto);

    CountryResponseDto actualResponseDto = countryService.getEntityById(id);

    verify(countryRepository).findById(id);
    verify(countryMapperService).entityToResponseDto(country);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void createEntity() {

    when(countryMapperService.requestDtoToEntity(requestEntityDTO)).thenReturn(country);
    when(countryRepository.save(country)).thenReturn(country);
    when(countryMapperService.entityToResponseDto(country)).thenReturn(expectedResponseDto);

    CountryResponseDto actualResponseDto = countryService.createEntity(requestEntityDTO);

    verify(countryMapperService).requestDtoToEntity(requestEntityDTO);
    verify(countryRepository).save(country);
    verify(countryMapperService).entityToResponseDto(country);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void updateEntity() {
    Long id = 1L;

    when(countryRepository.findById(id)).thenReturn(Optional.of(country));
    when(countryRepository.save(country)).thenReturn(country);
    when(countryMapperService.entityToResponseDto(country)).thenReturn(expectedResponseDto);

    CountryResponseDto actualResponseDto = countryService.updateEntity(id, requestEntityDTO);

    verify(countryRepository).findById(id);
    verify(countryRepository).save(country);
    verify(countryMapperService).updateEntityByRequestDto(requestEntityDTO, country);
    verify(countryMapperService).entityToResponseDto(country);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void findByName() {
    String name = country.getName();

    when(countryRepository.findByName(name)).thenReturn(Optional.of(country));
    when(countryMapperService.entityToResponseDto(country)).thenReturn(expectedResponseDto);

    CountryResponseDto actualResponseDto = countryService.findByName(name);

    verify(countryRepository).findByName(name);
    verify(countryMapperService).entityToResponseDto(country);

    assertEquals(expectedResponseDto, actualResponseDto);
  }
}