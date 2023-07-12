package com.danielvishnievskyi.soulsmatch.service.location;

import com.danielvishnievskyi.soulsmatch.mapper.location.LocationMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
  private final LocationRepository locationRepository;
  private final LocationMapperServiceImpl locationMapperService;

  @Override
  public LocationResponseDto getEntityById(Long id) {
    //TODO: custom exception
    return locationMapperService.entityToResponseDto(
      locationRepository.findById(id).orElseThrow()
    );
  }

  @Override
  public LocationResponseDto createEntity(LocationRequestDto requestEntityDTO) {
    return locationMapperService.entityToResponseDto(
      locationRepository.save(locationMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public LocationResponseDto updateEntity(Long id, LocationRequestDto requestEntityDTO) {
    Location location = locationRepository.findById(id).orElseThrow(); //TODO: custom exception
    locationMapperService.updateEntityByRequestDto(requestEntityDTO, location);
    return locationMapperService.entityToResponseDto(locationRepository.save(location));
  }

  @Override
  public void deleteEntityById(Long id) {
    locationRepository.deleteById(id);
  }
}
