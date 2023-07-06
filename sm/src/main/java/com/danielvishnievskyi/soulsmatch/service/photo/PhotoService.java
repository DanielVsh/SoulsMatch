package com.danielvishnievskyi.soulsmatch.service.photo;

import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.service.CrudService;

public interface PhotoService extends CrudService<PhotoResponseDto, PhotoRequestDto> {
}
