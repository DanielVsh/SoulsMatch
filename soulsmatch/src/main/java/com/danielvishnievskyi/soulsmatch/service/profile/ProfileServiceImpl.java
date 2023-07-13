package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.mapper.profile.ProfileMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.specification.LikeSpecification;
import com.danielvishnievskyi.soulsmatch.repository.specification.ProfileSpecification;
import com.danielvishnievskyi.soulsmatch.util.like.LikeServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.swipe.SwipeServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;
  private final ProfileMapperService profileMapperService;
  private final SwipeServiceUtil swipeServiceUtil;
  private final LikeServiceUtil likeServiceUtil;

  @Override
  public Page<ProfileResponseDto> getNextProfiles(String username) {
    Profile swiperProfile = profileRepository.findBySoulEmail(username).orElseThrow(); //TODO: custom exception

    Specification<Profile> specification = Specification.where(ProfileSpecification.withNotSwipedProfiles(username))
      .and(ProfileSpecification.withPreferredGenders(swiperProfile.getPreferredGenders()))
      .and(ProfileSpecification.withDistance(
        swiperProfile.getLocation().getLatitude(),
        swiperProfile.getLocation().getLongitude())
      );

    return profileRepository.findAll(specification, PageRequest.of(0, 10))
      .map(profileMapperService::entityToResponseDto);
  }

  @Override
  public Page<ProfileResponseDto> getRequestedLikesProfiles(String username, Pageable pageable) {
    Profile profile = profileRepository.findBySoulEmail(username).orElseThrow();//TODO: custom exception
    Specification<Profile> spec = LikeSpecification.findRequestedProfilesByLikedSoulUsername(username)
      .and(ProfileSpecification.withDistance(
        profile.getLocation().getLatitude(),
        profile.getLocation().getLongitude())
      );

    return profileRepository.findAll(spec, pageable)
      .map(profileMapperService::entityToResponseDto);
  }

  @Override
  public void dislikeProfile(String username, Long goalProfileId) {
    swipeServiceUtil.updateSwipedProfilesBySoul(username, goalProfileId);
  }

  @Override
  public void likeProfile(String username, Long goalProfileId) {
    likeServiceUtil.likeProfile(username, goalProfileId);
    swipeServiceUtil.updateSwipedProfilesBySoul(username, goalProfileId);
  }

  @Override
  public ProfileResponseDto getEntityById(Long id) {
    return profileMapperService.entityToResponseDto(
      profileRepository.findById(id).orElseThrow() //TODO: custom exception
    );
  }

  @Override
  public ProfileResponseDto createEntity(ProfileRequestDto requestEntityDTO) {
    return profileMapperService.entityToResponseDto(
      profileRepository.save(profileMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public ProfileResponseDto updateEntity(Long id, ProfileRequestDto requestEntityDTO) {
    Profile profile = profileRepository.findById(id).orElseThrow(); // TODO: custom exception
    profileMapperService.updateEntityByRequestDto(requestEntityDTO, profile);
    return profileMapperService.entityToResponseDto(profileRepository.save(profile));
  }

  @Override
  public void deleteEntityById(Long id) {
    profileRepository.deleteById(id);
  }
}
