package com.danielvishnievskyi.soulsmatch.service.soul;

import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;

public interface SoulService {
  SoulResponseDto findByUsername(String Username);

  SoulResponseDto getEntityById(Long id);

  void createEntity(SoulRequestDto requestEntityDTO);

  void updateEntity(Long id, SoulRequestDto requestEntityDTO);

  void deleteEntityById(Long id);
}
