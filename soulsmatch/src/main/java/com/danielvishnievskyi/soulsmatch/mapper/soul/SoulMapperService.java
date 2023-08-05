package com.danielvishnievskyi.soulsmatch.mapper.soul;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;

import java.util.List;

public interface SoulMapperService extends MapperService<Soul, SoulRequestDto, SoulResponseDto> {
  Soul mapUsernameToSoul(String username);
  List<Soul> mapUsernamesToSouls(List<String> usernames);
}
