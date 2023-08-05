package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.exception.profile.ProfileAlreadyExistsException;
import com.danielvishnievskyi.soulsmatch.mapper.profile.ProfileMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.specification.LikeSpecification;
import com.danielvishnievskyi.soulsmatch.repository.specification.ProfileSpecification;
import com.danielvishnievskyi.soulsmatch.util.auth.CurrentUserProvider;
import com.danielvishnievskyi.soulsmatch.util.like.LikeServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.location.LocationServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.photo.PhotoServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.swipe.SwipeServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;
  private final ProfileMapperService profileMapperService;
  private final SwipeServiceUtil swipeServiceUtil;
  private final LikeServiceUtil likeServiceUtil;
  private final LocationServiceUtil locationServiceUtil;
  private final PhotoServiceUtil photoServiceUtil;
  private final CurrentUserProvider currentUserProvider;

  @Override
  public Page<ProfileResponseDto> getNextProfiles(String username, Pageable pageable) {
    Profile swiperProfile = getProfileByUsernameOrElseThrow(username);

    Specification<Profile> specification = Specification.where(ProfileSpecification.withNotSwipedProfiles(username))
      .and(ProfileSpecification.withPreferredGenders(swiperProfile.getPreferredGenders()))
      .and(ProfileSpecification.withDistance(
        swiperProfile.getLocation().getLatitude(),
        swiperProfile.getLocation().getLongitude())
      );

    return profileRepository.findAll(specification, pageable)
      .map(profileMapperService::entityToResponseDto);
  }

  @Override
  public Page<ProfileResponseDto> getRequestedLikesProfiles(String username, Pageable pageable) {
    Profile profile = getProfileByUsernameOrElseThrow(username);
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
  public ProfileResponseDto getProfileByUsername(String username) {
    return profileMapperService.entityToResponseDto(getProfileByUsernameOrElseThrow(username));
  }

  @Override
  public void createProfile(ProfileRequestDto requestEntityDTO, Soul soul) {
    profileRepository.findBySoulUsername(soul.getUsername())
      .ifPresent(profile -> {
        throw new ProfileAlreadyExistsException("Profile already exists");
      });

    Profile entity = profileMapperService.requestDtoToEntity(requestEntityDTO);

    entity.setSoul(soul);

    Location createdLocation = locationServiceUtil.createIfNotFound(
      requestEntityDTO.getLocation().getName(),
      requestEntityDTO.getLocation().getLatitude(),
      requestEntityDTO.getLocation().getLongitude()
    );
    entity.setLocation(createdLocation);

    List<Photo> photos = photoServiceUtil.processPhotoRequestAndUpdatePhotos(
      requestEntityDTO.getPhotos(),
      soul.getUsername()
    );
    entity.setPhotos(photos);

    profileRepository.save(entity);
  }

  @Override
  public void updateProfile(Long id, ProfileRequestDto requestEntityDTO) {
    Profile profile = getProfileByIdOrElseThrow(id);

    profileMapperService.updateEntityByRequestDto(requestEntityDTO, profile);

    profile.setLocation(locationServiceUtil.createIfNotFound(
      requestEntityDTO.getLocation().getName(),
      requestEntityDTO.getLocation().getLatitude(),
      requestEntityDTO.getLocation().getLongitude())
    );

    List<Photo> photos = photoServiceUtil.processPhotoRequestAndUpdatePhotos(
      requestEntityDTO.getPhotos(),
      currentUserProvider.getCurrentUsername()
    );

    profile.getPhotos().addAll(photos);

    profileRepository.save(profile);
  }

  @Override
  public void deleteProfileById(Long id) {
    getProfileByIdOrElseThrow(id).getPhotos().forEach(photo ->
      photoServiceUtil.deletePhotoAndRemoveFromS3Bucket(photo.getId())
    );
    profileRepository.deleteById(id);
  }

  private Profile getProfileByIdOrElseThrow(Long id) {
    return profileRepository.findById(id).orElseThrow(); // TODO: custom exception
  }

  private Profile getProfileByUsernameOrElseThrow(String username) {
    return profileRepository.findBySoulUsername(username).orElseThrow(); // TODO: custom exception
  }
}
