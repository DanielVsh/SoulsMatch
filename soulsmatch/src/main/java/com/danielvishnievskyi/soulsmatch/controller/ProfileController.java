package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@PreAuthorize("hasAnyRole('USER')")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/next")
  public ResponseEntity<Page<ProfileResponseDto>> getNextProfiles(
    @AuthenticationPrincipal Soul soul,
    Pageable pageable
  ) {
    return ResponseEntity.ok(profileService.getNextProfiles(soul.getUsername(), pageable));
  }

  @GetMapping()
  public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal Soul soul) {
    return ResponseEntity.ok(profileService.getProfileByUsername(soul.getUsername()));
  }

  @GetMapping("/liked")
  public ResponseEntity<Page<ProfileResponseDto>> getLikedProfiles(
    @AuthenticationPrincipal Soul soul,
    Pageable pageable
  ) {
    return ResponseEntity.ok(profileService.getRequestedLikesProfiles(soul.getUsername(), pageable));
  }

  @PatchMapping("/{profileId}/dislike")
  public ResponseEntity<Void> dislikeProfile(
    @PathVariable(name = "profileId") Long targetId,
    @AuthenticationPrincipal Soul soul
  ) {
    profileService.dislikeProfile(soul.getUsername(), targetId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{profileId}/like")
  public ResponseEntity<Void> likeProfile(
    @PathVariable(name = "profileId") Long targetId,
    @AuthenticationPrincipal Soul soul
  ) {
    profileService.likeProfile(soul.getUsername(), targetId);
    return ResponseEntity.ok().build();
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> createProfile(
    @ModelAttribute @Valid ProfileRequestDto profileRequestDto,
    @AuthenticationPrincipal Soul soul
  ) {
    profileService.createProfile(profileRequestDto, soul);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body("Profile successfully created");
  }
}
