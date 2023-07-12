package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.mapper.profile.ProfileMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
@PreAuthorize("hasAnyRole('USER')")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;
  private final ProfileRepository profileRepository;
  private final ProfileMapperServiceImpl profileMapperService;

  @GetMapping("/next")
  public ResponseEntity<List<ProfileResponseDto>> getNextProfiles(Principal principal) {
    return ResponseEntity.ok(profileService.getNextProfiles(principal.getName()));
  }

  @GetMapping()
  public ResponseEntity<ProfileResponseDto> getProfile(Principal principal) {
    return ResponseEntity.ok(profileMapperService.entityToResponseDto(profileRepository.findBySoulEmail(principal.getName()).orElseThrow()));
  }

  @GetMapping("/liked")
  public ResponseEntity<Page<ProfileResponseDto>> getLikedProfiles(Principal principal) {
    return ResponseEntity.ok(profileService.getRequestedLikesProfiles(principal.getName(), PageRequest.of(0, 10)));
  }

  @PatchMapping("/{profileId}/dislike")
  public ResponseEntity<Void> dislikeProfile(
    @PathVariable(name = "profileId") Long targetId,
    Principal principal
  ) {
    profileService.dislikeProfile(principal.getName(), targetId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{profileId}/like")
  public ResponseEntity<Void> likeProfile(
    @PathVariable(name = "profileId") Long targetId,
    Principal principal
  ) {
    profileService.likeProfile(principal.getName(), targetId);
    return ResponseEntity.ok().build();
  }

  @PostMapping()
  public ResponseEntity<ProfileResponseDto> createProfile(@RequestBody ProfileRequestDto profileRequestDto) {
    return ResponseEntity.ok(profileService.createEntity(profileRequestDto));
  }
}
