package com.danielvishnievskyi.soulsmatch.service.auth;

import com.danielvishnievskyi.soulsmatch.model.dto.request.AuthenticationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.RegisterRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.AuthenticationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

  AuthenticationResponseDto register(RegisterRequestDto requestDto);

  AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto);

  void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
