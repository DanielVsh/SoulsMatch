package com.danielvishnievskyi.soulsmatch.service.location;

import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.model.entity.Country;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.mapper.LocationMapperService;
import com.danielvishnievskyi.soulsmatch.repository.LocationRepository;
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
class LocationServiceImplTest {

  @Mock
  private LocationRepository locationRepository;

  @Mock
  private LocationMapperService locationMapperService;

  @InjectMocks
  private LocationServiceImpl locationService;

  private Location location;
  private LocationResponseDto mockedResponseDto;
  private LocationRequestDto mockedRequestDto;

  @BeforeEach
  void beforeEach() {
    City city1 = new City(1L, "TestCity1", 1, 2);
    City city2 = new City(2L, "TestCity2", 5, 6);

    Country country = new Country(1L, "TestCountry", List.of(city1, city2));

    mockedResponseDto = new LocationResponseDto(
      1L,
      new CountryResponseDto(1L,
        "TestCountry",
        List.of(
          new CityResponseDto(1L, "TestCity1", 1, 2),
          new CityResponseDto(2L, "TestCity2", 5 ,6)
        )),
      new CityResponseDto(1L, "TestCity1", 1, 2),
      11,
      44
    );

    mockedRequestDto = new LocationRequestDto(1L, 1L, 11, 44);

    location = new Location(1L, country, city1, 11, 44);
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
    when(locationMapperService.entityToResponseDto(location)).thenReturn(mockedResponseDto);

    LocationResponseDto result = locationService.getEntityById(1L);

    assertEquals(mockedResponseDto, result);
    verify(locationRepository).findById(1L);
  }

  @Test
  void createEntity() {
    when(locationMapperService.requestDtoToEntity(mockedRequestDto)).thenReturn(location);
    when(locationRepository.save(location)).thenReturn(location);
    when(locationMapperService.entityToResponseDto(location)).thenReturn(mockedResponseDto);

    LocationResponseDto actualResponseDto = locationService.createEntity(mockedRequestDto);

    verify(locationMapperService).requestDtoToEntity(mockedRequestDto);
    verify(locationRepository).save(location);
    verify(locationMapperService).entityToResponseDto(location);

    assertEquals(mockedResponseDto, actualResponseDto);
  }

  @Test
  void updateEntity() {
    Long id = 1L;

    when(locationRepository.findById(id)).thenReturn(Optional.of(location));
    when(locationRepository.save(location)).thenReturn(location);
    when(locationMapperService.entityToResponseDto(location)).thenReturn(mockedResponseDto);

    LocationResponseDto actualResponseDto = locationService.updateEntity(id, mockedRequestDto);

    verify(locationRepository).findById(id);
    verify(locationRepository).save(location);
    verify(locationMapperService).updateEntityByRequestDto(mockedRequestDto, location);
    verify(locationMapperService).entityToResponseDto(location);

    assertEquals(mockedResponseDto, actualResponseDto);
  }

}