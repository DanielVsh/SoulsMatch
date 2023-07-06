package com.danielvishnievskyi.soulsmatch.service.country;

import com.danielvishnievskyi.soulsmatch.mapper.CountryMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.CountryRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Country;
import com.danielvishnievskyi.soulsmatch.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{

  private final CountryRepository countryRepository;
  private final CountryMapperService countryMapperService;

  @Override
  public CountryResponseDto getEntityById(Long id) {
    //TODO: custom Exception
    return countryMapperService.entityToResponseDto(countryRepository.findById(id).orElseThrow());
  }

  @Override
  public CountryResponseDto createEntity(CountryRequestDto requestEntityDTO) {
    return countryMapperService.entityToResponseDto(
      countryRepository.save(countryMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public CountryResponseDto updateEntity(Long id, CountryRequestDto requestEntityDTO) {
    //TODO: custom exception
    Country country = countryRepository.findById(id).orElseThrow();
    countryMapperService.updateEntityByRequestDto(requestEntityDTO, country);
    return countryMapperService.entityToResponseDto(countryRepository.save(country));
  }

  @Override
  public void deleteEntityById(Long id) {
    countryRepository.deleteById(id);
  }


  @Override
  public CountryResponseDto findByName(String name) {
    //TODO: custom exception
    return countryMapperService.entityToResponseDto(countryRepository.findByName(name).orElseThrow());
  }
}
