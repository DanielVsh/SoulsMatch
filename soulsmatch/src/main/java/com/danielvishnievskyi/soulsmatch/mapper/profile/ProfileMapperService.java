package com.danielvishnievskyi.soulsmatch.mapper.profile;

import com.danielvishnievskyi.soulsmatch.mapper.MapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;

public interface ProfileMapperService extends MapperService<Profile, ProfileRequestDto, ProfileResponseDto> {
}
