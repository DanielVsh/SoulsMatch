package com.danielvishnievskyi.soulsmatch.service.profile;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.*;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.mapper.ProfileMapperService;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.danielvishnievskyi.soulsmatch.model.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

  @Mock
  private ProfileRepository profileRepository;

  @Mock
  private ProfileMapperService profileMapperService;

  @InjectMocks
  private ProfileServiceImpl profileService;

  private Profile profile;
  private ProfileRequestDto profileRequestDto;
  private ProfileResponseDto profileResponseDto;


  @BeforeEach
  void beforeEach() {
    profile = new Profile(
      1L,
      new Soul(),
      "description",
      List.of(),
      List.of()
    );

    profileRequestDto = new ProfileRequestDto(1L, "description", List.of(), List.of());

    profileResponseDto = new ProfileResponseDto(
      1L,
      new SoulResponseDto(
        1L,
        "soul",
        "soul",
        "name",
        MALE,
        18,
        new LocationResponseDto(
          1L,
          new CountryResponseDto(1L, "Country", List.of()),
          new CityResponseDto(1L, "City", 11, 12),
          55,
          66
        ),
        4.6F,
        List.of()
      ),
      "description",
      List.of()
    );
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
    when(profileMapperService.entityToResponseDto(profile)).thenReturn(profileResponseDto);

    ProfileResponseDto result = profileService.getEntityById(1L);

    assertEquals(profileResponseDto, result);
    verify(profileRepository).findById(1L);
  }

  @Test
  void createEntity() {
    when(profileMapperService.requestDtoToEntity(profileRequestDto)).thenReturn(profile);
    when(profileRepository.save(profile)).thenReturn(profile);
    when(profileMapperService.entityToResponseDto(profile)).thenReturn(profileResponseDto);

    ProfileResponseDto actualResponseDto = profileService.createEntity(profileRequestDto);

    verify(profileMapperService).requestDtoToEntity(profileRequestDto);
    verify(profileRepository).save(profile);
    verify(profileMapperService).entityToResponseDto(profile);

    assertEquals(profileResponseDto, actualResponseDto);
  }

  @Test
  void updateEntity() {
    Long id = 1L;
    ProfileResponseDto updatedProfileResponseDto = new ProfileResponseDto(
      1L,
      new SoulResponseDto(
        1L,
        "soul",
        "soul",
        "name",
        MALE,
        18,
        new LocationResponseDto(
          1L,
          new CountryResponseDto(1L, "Country", List.of()),
          new CityResponseDto(1L, "City", 11, 12),
          55,
          66
        ),
        4.6F,
        List.of()
      ),
      "test test test",
      List.of()
    );

    Profile updatedProfile = new Profile(
      1L,
      new Soul(),
      "test test test",
      List.of(),
      List.of()
    );

    ProfileRequestDto updateProfileRequestDto = new ProfileRequestDto(1L, "test test test", List.of(), List.of());


    ArgumentCaptor<ProfileRequestDto> requestDtoCaptor = ArgumentCaptor.forClass(ProfileRequestDto.class);

    when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
    when(profileRepository.save(profile)).thenReturn(updatedProfile);

    when(profileMapperService.entityToResponseDto(updatedProfile)).thenReturn(updatedProfileResponseDto);

    ProfileResponseDto actualResponseDto = profileService.updateEntity(id, updateProfileRequestDto);

    verify(profileRepository).findById(id);
    verify(profileRepository).save(profile);
    verify(profileMapperService).updateEntityByRequestDto(requestDtoCaptor.capture(), eq(profile));
    verify(profileMapperService).entityToResponseDto(updatedProfile);

    ProfileRequestDto capturedRequestDto = requestDtoCaptor.getValue();

    assertEquals(updateProfileRequestDto, capturedRequestDto);
    assertEquals(updatedProfileResponseDto, actualResponseDto);
  }
}