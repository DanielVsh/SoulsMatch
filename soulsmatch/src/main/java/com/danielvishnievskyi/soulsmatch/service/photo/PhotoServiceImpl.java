package com.danielvishnievskyi.soulsmatch.service.photo;

import com.danielvishnievskyi.soulsmatch.mapper.photo.PhotoMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
  private final PhotoRepository photoRepository;
  private final PhotoMapperService photoMapperService;

  @Override
  public PhotoResponseDto getEntityById(Long id) {
    //TODO: custom exception
    return photoMapperService.entityToResponseDto(
      photoRepository.findById(id).orElseThrow()
    );
  }

  @Override
  public PhotoResponseDto createEntity(PhotoRequestDto requestEntityDTO) {
    return photoMapperService.entityToResponseDto(
      photoRepository.save(photoMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public PhotoResponseDto updateEntity(Long id, PhotoRequestDto requestEntityDTO) {
    Photo photo = photoRepository.findById(id).orElseThrow();//TODO: custom exception
    photoMapperService.updateEntityByRequestDto(requestEntityDTO, photo);
    return photoMapperService.entityToResponseDto(photoRepository.save(photo));
  }

  @Override
  public void deleteEntityById(Long id) {
    photoRepository.deleteById(id);
  }
}
