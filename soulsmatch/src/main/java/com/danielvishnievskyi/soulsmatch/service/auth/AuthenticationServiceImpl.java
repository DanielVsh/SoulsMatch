package com.danielvishnievskyi.soulsmatch.service.auth;

import com.danielvishnievskyi.soulsmatch.exception.soul.SoulAlreadyExistsException;
import com.danielvishnievskyi.soulsmatch.model.dto.request.AuthenticationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.RegisterRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.AuthenticationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.enums.Role;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.security.jwt.JwtUtils;
import com.danielvishnievskyi.soulsmatch.util.auth.AuthenticationServiceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final SoulRepository soulRepository;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final AuthenticationServiceUtil authenticationServiceUtil;
  private final PasswordEncoder passwordEncoder;

  @Override
  public AuthenticationResponseDto register(RegisterRequestDto requestDto) {
    soulRepository.findByUsername(requestDto.getUsername())
      .ifPresent(soul -> {throw new SoulAlreadyExistsException("User with provided username already exists: %s"
        .formatted(requestDto.getUsername()));
      });

    Soul soul = Soul.builder()
      .username(requestDto.getUsername())
      .password(passwordEncoder.encode(requestDto.getPassword()))
      .firstName(requestDto.getFirstName())
      .lastName(requestDto.getLastName())
      .birthDate(LocalDate.parse(requestDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
      .gender(requestDto.getGender())
      .roles(List.of(Role.USER))
      .build();
    Soul savedSoul = soulRepository.save(soul);
    String jwtToken = jwtUtils.generateToken(soul);
    String refreshToken = jwtUtils.generateRefreshToken(soul);
    authenticationServiceUtil.saveSoulToken(savedSoul, jwtToken);
    return new AuthenticationResponseDto(jwtToken, refreshToken);
  }

  @Override
  public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
    );
    Soul soul = soulRepository.findByUsername(requestDto.getUsername()).orElseThrow();//TODO: custom exception
    String jwtToken = jwtUtils.generateToken(soul);
    String refreshToken = jwtUtils.generateRefreshToken(soul);
    authenticationServiceUtil.deleteAllSoulTokens(soul);
    authenticationServiceUtil.saveSoulToken(soul, jwtToken);
    return new AuthenticationResponseDto(jwtToken, refreshToken);
  }

  @Override
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    final String refreshToken;
    final String username;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    username = jwtUtils.extractUsername(refreshToken);
    if (username != null) {
      Soul soul = soulRepository.findByUsername(username).orElseThrow(); //TODO: custom exception
      if (jwtUtils.isTokenValid(refreshToken, soul)) {
        String accessToken = jwtUtils.generateToken(soul);
        authenticationServiceUtil.deleteAllSoulTokens(soul);
        authenticationServiceUtil.saveSoulToken(soul, accessToken);
        var authResponse = new AuthenticationResponseDto(accessToken, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
