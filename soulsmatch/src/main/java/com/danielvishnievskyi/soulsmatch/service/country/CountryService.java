package com.danielvishnievskyi.soulsmatch.service.country;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CountryRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CountryResponseDto;
import com.danielvishnievskyi.soulsmatch.service.CrudService;

public interface CountryService extends CrudService<CountryResponseDto, CountryRequestDto> {
  CountryResponseDto findByName(String name);
}
