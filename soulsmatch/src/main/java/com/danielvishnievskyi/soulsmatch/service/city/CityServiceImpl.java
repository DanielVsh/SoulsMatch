package com.danielvishnievskyi.soulsmatch.service.city;

import com.danielvishnievskyi.soulsmatch.exception.CityNotFoundException;
import com.danielvishnievskyi.soulsmatch.mapper.CityMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.CityRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.City;
import com.danielvishnievskyi.soulsmatch.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService{

  private final CityRepository cityRepository;
  private final CityMapperService cityMapperService;


  @Override
  public CityResponseDto getEntityById(Long id) {
    return cityMapperService.entityToResponseDto(cityRepository.findById(id).orElseThrow());
  }

  @Override
  public CityResponseDto createEntity(CityRequestDto requestEntityDTO) {
    return cityMapperService.entityToResponseDto(
      cityRepository.save(cityMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public CityResponseDto updateEntity(Long id, CityRequestDto requestEntityDTO) {
    City city = cityRepository.findById(id).orElseThrow();
    //TODO: custom exception
    cityMapperService.updateEntityByRequestDto(requestEntityDTO, city);
    return cityMapperService.entityToResponseDto(cityRepository.save(city));
  }

  @Override
  public void deleteEntityById(Long id) {
    cityRepository.deleteById(id);
  }

  @Override
  public CityResponseDto findByName(String name) {
    return cityMapperService.entityToResponseDto(
      cityRepository.findByName(name)
        .orElseThrow(() -> new CityNotFoundException(String.format("City %s does not exists", name)))
    );
  }
}
