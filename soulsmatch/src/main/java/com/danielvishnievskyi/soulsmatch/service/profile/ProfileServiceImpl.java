package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.component.swipe.SwipeComponent;
import com.danielvishnievskyi.soulsmatch.mapper.ProfileMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.specification.ProfileSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;
  private final ProfileMapperService profileMapperService;
  private final SwipeComponent swipeComponent;

  @Override
  public List<ProfileResponseDto> getNextProfiles(String username) {
    Profile swiperProfile = profileRepository.findBySoulEmail(username).orElseThrow(); //TODO: custom exception

    Specification<Profile> specification = Specification.where(ProfileSpecification.withNotSwipedProfiles(username))
      .and(ProfileSpecification.withCountry(swiperProfile.getSoul().getLocation().getCountry().getName()))
      .and(ProfileSpecification.withPreferredGenders(swiperProfile.getPreferredGenders()))
      .and(ProfileSpecification.withDistance(
        swiperProfile.getSoul().getLocation().getLatitude(),
        swiperProfile.getSoul().getLocation().getLongitude())
      );

    return profileRepository.findAll(specification, PageRequest.of(0, 10)).stream()
      .map(profileMapperService::entityToResponseDto)
      .collect(Collectors.toList());
  }

  @Override
  public void dislikeProfile(String username, Long goalProfileId) {
    swipeComponent.updateSwipedProfilesBySoul(username, goalProfileId);
  }

  @Override
  public void likeProfile(String username, Long goalProfileId) {
    //TODO: like system
    swipeComponent.updateSwipedProfilesBySoul(username, goalProfileId);
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
