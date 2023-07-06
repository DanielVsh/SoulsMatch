package com.danielvishnievskyi.soulsmatch.service.city;

import com.danielvishnievskyi.soulsmatch.model.dto.request.CityRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.CityResponseDto;
import com.danielvishnievskyi.soulsmatch.service.CrudService;

public interface CityService extends CrudService<CityResponseDto, CityRequestDto> {
  CityResponseDto findByName(String name);
}
