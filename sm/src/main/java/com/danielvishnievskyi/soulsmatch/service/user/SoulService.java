package com.danielvishnievskyi.soulsmatch.service.user;

import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.service.CrudService;

public interface SoulService extends CrudService<SoulResponseDto, SoulRequestDto> {
  SoulResponseDto findByUsername(String Username);
}
