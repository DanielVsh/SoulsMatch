package com.danielvishnievskyi.soulsmatch.mapper.photo;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;

public interface PhotoMapperService extends MapperService<Photo, PhotoRequestDto, PhotoResponseDto> {
}
