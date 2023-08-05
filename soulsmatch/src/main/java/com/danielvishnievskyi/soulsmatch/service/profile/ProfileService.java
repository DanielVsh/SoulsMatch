package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {
  Page<ProfileResponseDto> getNextProfiles(String username, Pageable pageable);

  Page<ProfileResponseDto> getRequestedLikesProfiles(String username, Pageable pageable);

  ProfileResponseDto getProfileByUsername(String username);

  void createProfile(ProfileRequestDto requestEntityDTO, Soul soul);

  void updateProfile(Long id, ProfileRequestDto requestEntityDTO);

  void deleteProfileById(Long id);

  void dislikeProfile(String username, Long profileId);

  void likeProfile(String username, Long profileId);
}
