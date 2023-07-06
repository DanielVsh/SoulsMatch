package com.danielvishnievskyi.soulsmatch.service.city;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CityRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.mapper.CityMapperService;
import com.danielvishnievskyi.soulsmatch.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

  @Mock
  private CityRepository cityRepository;

  @Mock
  private CityMapperService cityMapperService;

  @InjectMocks
  private CityServiceImpl cityService;

  private City city;

  @BeforeEach
  void beforeEach() {
    city = new City(1L, "TestCity1", 1, 2);
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

    CityResponseDto mockedResponseDto = new CityResponseDto(1L, "TestCity1", 1, 2);
    when(cityMapperService.entityToResponseDto(city)).thenReturn(mockedResponseDto);

    CityResponseDto entityById = cityService.getEntityById(1L);

    assertEquals(mockedResponseDto.id(), entityById.id());
    verify(cityRepository).findById(1L);
  }

  @Test
  void createEntity() {
    CityRequestDto mockedRequestDto = new CityRequestDto("TestCity1", 1, 2);
    when(cityMapperService.requestDtoToEntity(mockedRequestDto)).thenReturn(city);

    when(cityRepository.save(city)).thenReturn(city);

    CityResponseDto mockedResponseDto = new CityResponseDto(1L, "TestCity1", 1, 2);
    when(cityMapperService.entityToResponseDto(city)).thenReturn(mockedResponseDto);

    CityResponseDto entity = cityService.createEntity(mockedRequestDto);

    assertEquals(entity.name(), mockedRequestDto.name());
    assertEquals(entity.latitude(), mockedRequestDto.latitude());
    assertEquals(entity.longitude(), mockedRequestDto.longitude());

    verify(cityMapperService).requestDtoToEntity(mockedRequestDto);
    verify(cityMapperService).entityToResponseDto(city);
    verify(cityRepository).save(city);
  }

  @Test
  public void updateEntity() {
    Long id = 1L;
    CityRequestDto requestEntityDTO = new CityRequestDto(null, 19999, 11111);
    CityResponseDto expectedResponseDto = new CityResponseDto(1L, "TestCity1", 19999, 11111);

    City expectedCity = new City(1L, "TestCity1", 19999, 11111);

    when(cityRepository.findById(id)).thenReturn(Optional.of(city));
    when(cityRepository.save(any(City.class))).thenReturn(expectedCity);
    when(cityMapperService.entityToResponseDto(expectedCity)).thenReturn(expectedResponseDto);

    CityResponseDto actualResponseDto = cityService.updateEntity(id, requestEntityDTO);

    verify(cityRepository).findById(id);
    verify(cityRepository).save(any(City.class));
    verify(cityMapperService).updateEntityByRequestDto(eq(requestEntityDTO), same(city));
    verify(cityMapperService).entityToResponseDto(same(expectedCity));

    assertEquals(expectedResponseDto, actualResponseDto);
    assertEquals(requestEntityDTO.latitude(), actualResponseDto.latitude());
    assertEquals(requestEntityDTO.longitude(), actualResponseDto.longitude());
    assertEquals(city.getName(), actualResponseDto.name());
  }

  @Test
  void findByName() {
    CityResponseDto expectedResponseDto = new CityResponseDto(1L, "TestCity1", 19999, 11111);

    when(cityRepository.findByName("TestCity1")).thenReturn(Optional.of(city));
    when(cityMapperService.entityToResponseDto(city)).thenReturn(expectedResponseDto);

    CityResponseDto actualResponseDto = cityService.findByName("TestCity1");

    verify(cityRepository).findByName("TestCity1");
    verify(cityMapperService).entityToResponseDto(city);

    assertEquals(expectedResponseDto, actualResponseDto);
  }
}