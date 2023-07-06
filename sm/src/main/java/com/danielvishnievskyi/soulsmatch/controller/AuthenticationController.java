package com.danielvishnievskyi.soulsmatch.controller;

import com.danielvishnievskyi.soulsmatch.model.dto.request.AuthenticationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.RegisterRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.AuthenticationResponseDto;
import com.danielvishnievskyi.soulsmatch.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponseDto> register(
    @RequestBody RegisterRequestDto requestDto) {
    return ResponseEntity.ok(authenticationService.register(requestDto));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponseDto> authenticate(
    @RequestBody AuthenticationRequestDto requestDto) {
    return ResponseEntity.ok(authenticationService.authenticate(requestDto));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    authenticationService.refreshToken(request, response);
  }
}
