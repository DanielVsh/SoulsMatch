package com.danielvishnievskyi.soulsmatch.mapper.location;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;

public interface LocationMapperService extends MapperService<Location, LocationRequestDto, LocationResponseDto> {
}
