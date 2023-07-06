package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.service.CrudService;

import java.util.List;

public interface ProfileService extends CrudService<ProfileResponseDto, ProfileRequestDto> {
  List<ProfileResponseDto> getNextProfiles(String username);

  void dislikeProfile(String username, Long profileId);

  void likeProfile(String username, Long profileId);
}
