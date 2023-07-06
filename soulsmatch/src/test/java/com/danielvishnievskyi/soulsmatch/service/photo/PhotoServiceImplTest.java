package com.danielvishnievskyi.soulsmatch.service.photo;

import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.mapper.PhotoMapperService;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoServiceImplTest {

  @Mock
  private PhotoRepository photoRepository;

  @Mock
  private PhotoMapperService photoMapperService;

  @InjectMocks
  private PhotoServiceImpl photoService;

  private Photo photo;
  private PhotoRequestDto photoRequestDto;
  private PhotoResponseDto photoResponseDto;

  @BeforeEach
  void beforeEach() {
    photo = new Photo(1L, "url");
    photoRequestDto = new PhotoRequestDto("url");
    photoResponseDto = new PhotoResponseDto(1L, "url");
  }

  @Test
  void getFilteredAndPageableList() {
  }

  @Test
  void getEntityById() {
    when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
    when(photoMapperService.entityToResponseDto(photo)).thenReturn(photoResponseDto);

    PhotoResponseDto result = photoService.getEntityById(1L);

    assertEquals(photoResponseDto, result);
    verify(photoRepository).findById(1L);
  }

  @Test
  void createEntity() {
    when(photoRepository.save(photo)).thenReturn(photo);
    when(photoMapperService.requestDtoToEntity(photoRequestDto)).thenReturn(photo);
    when(photoMapperService.entityToResponseDto(photo)).thenReturn(photoResponseDto);

    PhotoResponseDto actualResponseDto = photoService.createEntity(photoRequestDto);

    verify(photoRepository).save(photo);
    verify(photoMapperService).requestDtoToEntity(photoRequestDto);
    verify(photoMapperService).entityToResponseDto(photo);

    assertEquals(photoResponseDto, actualResponseDto);
  }

  @Test
  void updateEntity() {
    Long id = 1L;

    when(photoRepository.findById(id)).thenReturn(Optional.of(photo));
    when(photoRepository.save(photo)).thenReturn(photo);
    when(photoMapperService.entityToResponseDto(photo)).thenReturn(photoResponseDto);

    PhotoResponseDto actualResponseDto = photoService.updateEntity(id, photoRequestDto);

    verify(photoRepository).findById(id);
    verify(photoRepository).save(photo);
    verify(photoMapperService).updateEntityByRequestDto(photoRequestDto, photo);
    verify(photoMapperService).entityToResponseDto(photo);

    assertEquals(photoResponseDto, actualResponseDto);
  }
}