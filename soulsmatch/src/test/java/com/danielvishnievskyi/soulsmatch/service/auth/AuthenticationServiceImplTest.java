package com.danielvishnievskyi.soulsmatch.service.auth;

import com.danielvishnievskyi.soulsmatch.exception.soul.SoulAlreadyExistsException;
import com.danielvishnievskyi.soulsmatch.model.dto.request.AuthenticationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.RegisterRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.AuthenticationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import com.danielvishnievskyi.soulsmatch.model.enums.Role;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.security.jwt.JwtUtils;
import com.danielvishnievskyi.soulsmatch.util.auth.AuthenticationServiceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock
  private SoulRepository soulRepository;

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private AuthenticationServiceUtil authenticationServiceUtil;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Test
  void Register_SuccessRegister() {
    RegisterRequestDto requestDto = new RegisterRequestDto(
      "usEr1@gmail.com",
      "superSecuredPassword",
      "user",
      "NaMe",
      Gender.MALE,
      "2002-10-22");

    when(soulRepository.findByUsername(requestDto.getUsername()))
      .thenReturn(Optional.empty());

    when(passwordEncoder.encode(requestDto.getPassword()))
      .thenReturn("superSecuredAndEncodedPassword");

    when(jwtUtils.generateToken(any(Soul.class)))
      .thenReturn("sampleToken");
    when(jwtUtils.generateRefreshToken(any(Soul.class)))
      .thenReturn("sampleRefreshToken");

    AuthenticationResponseDto register = authenticationService.register(requestDto);

    ArgumentCaptor<Soul> soulArgumentCaptor = ArgumentCaptor.forClass(Soul.class);
    verify(soulRepository).save(soulArgumentCaptor.capture());
    Soul captorValue = soulArgumentCaptor.getValue();

    verify(authenticationServiceUtil).saveSoulToken(any(), any());

    assertEquals("user1@gmail.com", captorValue.getUsername());
    assertEquals("superSecuredAndEncodedPassword", captorValue.getPassword());
    assertEquals("User", captorValue.getFirstName());
    assertEquals("Name", captorValue.getLastName());
    assertEquals(LocalDate.parse("2002-10-22"), captorValue.getBirthDate());
    assertEquals(Gender.MALE, captorValue.getGender());
    assertEquals(List.of(Role.USER), captorValue.getRoles());

    assertNotNull(register);
    assertNotNull(register.getAccessToken());
    assertNotNull(register.getRefreshToken());
  }

  @Test
  void Register_UserExistsException() {
    RegisterRequestDto requestDto = new RegisterRequestDto();
    requestDto.setUsername("user1@gmail.com");

    Soul soul = new Soul();
    soul.setUsername("user1@gmail.com");

    when(soulRepository.findByUsername(requestDto.getUsername()))
      .thenReturn(Optional.of(soul));

    assertThrows(SoulAlreadyExistsException.class, () -> authenticationService.register(requestDto));
  }

  @Test
  void Authenticate_Success() {
    AuthenticationRequestDto request = new AuthenticationRequestDto();
    request.setUsername("user1@gmail.com");
    request.setPassword("superSecuredPassword1!");

    Soul soul = new Soul();
    soul.setUsername(request.getUsername());
    soul.setId(1L);

    when(soulRepository.findByUsername(request.getUsername()))
      .thenReturn(Optional.of(soul));

    String newAccessToken = "newAccessToken";
    when(jwtUtils.generateToken(soul)).thenReturn(newAccessToken);
    String newRefreshToken = "newRefreshToken";
    when(jwtUtils.generateRefreshToken(soul)).thenReturn(newRefreshToken);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
      .thenReturn(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    AuthenticationResponseDto responseDto = authenticationService.authenticate(request);

    assertEquals(newAccessToken, responseDto.getAccessToken());
    assertEquals(newRefreshToken, responseDto.getRefreshToken());

    verify(authenticationServiceUtil).deleteAllSoulTokens(soul);
    verify(authenticationServiceUtil).saveSoulToken(soul, newAccessToken);
    verify(authenticationManager).authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
  }

  @Test
  void RefreshToken_Success() throws IOException {

  }
}