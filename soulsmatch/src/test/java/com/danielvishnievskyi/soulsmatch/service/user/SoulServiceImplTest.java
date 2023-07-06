package com.danielvishnievskyi.soulsmatch.service.user;

import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import com.danielvishnievskyi.soulsmatch.mapper.SoulMapperService;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoulServiceImplTest {

  @Mock
  private SoulRepository soulRepository;

  @Mock
  private SoulMapperService soulMapperService;

  @InjectMocks
  private SoulServiceImpl userEntityService;

  @Test
  void findByUsername() {
    String username = "testuser";

    Soul soul = new Soul();
    soul.setEmail(username);

    SoulResponseDto expectedResponseDto = new SoulResponseDto(
      1L, username, null, null, Gender.MALE,18, null, 6.6F, null
    );

    when(soulRepository.findByEmail(username)).thenReturn(Optional.of(soul));

    when(soulMapperService.entityToResponseDto(soul)).thenReturn(expectedResponseDto);

    SoulResponseDto actualResponseDto = userEntityService.findByUsername(username);

    verify(soulRepository).findByEmail(username);
    verify(soulMapperService).entityToResponseDto(soul);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    Long id = 1L;

    Soul soul = new Soul();
    soul.setId(id);

    SoulResponseDto expectedResponseDto = new SoulResponseDto(
      id,  null, null, null, Gender.MALE,11, null, 4, null
    );

    when(soulRepository.findById(id)).thenReturn(Optional.of(soul));

    when(soulMapperService.entityToResponseDto(soul)).thenReturn(expectedResponseDto);

    SoulResponseDto actualResponseDto = userEntityService.getEntityById(id);

    verify(soulRepository).findById(id);
    verify(soulMapperService).entityToResponseDto(soul);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void createEntity() {
    SoulRequestDto requestDto = new SoulRequestDto(
      "soul", "123", "h", "f", Gender.MALE,11, null, 1, List.of()
    );

    Soul soul = new Soul();

    SoulResponseDto expectedResponseDto = new SoulResponseDto(
      1L, "soul", "h", "f", Gender.MALE,11, null, 1, List.of()
    );

    when(soulMapperService.requestDtoToEntity(requestDto)).thenReturn(soul);
    when(soulRepository.save(soul)).thenReturn(soul);
    when(soulMapperService.entityToResponseDto(soul)).thenReturn(expectedResponseDto);

    SoulResponseDto actualResponseDto = userEntityService.createEntity(requestDto);

    verify(soulMapperService).requestDtoToEntity(requestDto);
    verify(soulRepository).save(soul);
    verify(soulMapperService).entityToResponseDto(soul);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void updateEntity() {
    Long id = 1L;

    SoulRequestDto requestDto = new SoulRequestDto(
      "soul", "123", "f", "f", Gender.MALE,11, null, 1, List.of()
    );

    Soul soul = new Soul();

    SoulResponseDto expectedResponseDto = new SoulResponseDto(
      1L, "soul", "f", "f", Gender.MALE,11, null, 1, List.of()
    );

    when(soulRepository.findById(id)).thenReturn(Optional.of(soul));
    when(soulRepository.save(soul)).thenReturn(soul);

    doNothing().when(soulMapperService).updateEntityByRequestDto(requestDto, soul);
    when(soulMapperService.entityToResponseDto(soul)).thenReturn(expectedResponseDto);

    SoulResponseDto actualResponseDto = userEntityService.updateEntity(id, requestDto);

    verify(soulRepository).findById(id);
    verify(soulRepository).save(soul);
    verify(soulMapperService).updateEntityByRequestDto(requestDto, soul);
    verify(soulMapperService).entityToResponseDto(soul);

    assertEquals(expectedResponseDto, actualResponseDto);
  }

  @Test
  void deleteEntityById() {
    Long id = 1L;

    userEntityService.deleteEntityById(id);

    verify(soulRepository).deleteById(id);
  }
}
