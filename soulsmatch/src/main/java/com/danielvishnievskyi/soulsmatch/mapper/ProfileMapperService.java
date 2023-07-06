package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.component.photo.PhotoComponent;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.util.CurrentUserProvider;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class ProfileMapperService implements MapperService<Profile, ProfileRequestDto, ProfileResponseDto> {

  @Autowired
  protected SoulRepository soulRepository;

  @Autowired
  protected PhotoRepository photoRepository;

  @Autowired
  protected SoulMapperService soulMapperService;

  @Autowired
  protected PhotoMapperService photoMapperService;

  @Autowired
  protected PhotoComponent photoComponent;

  @Autowired
  protected CurrentUserProvider currentUserProvider;

  @Override
  @Mapping(target = "soul", expression = "java(soulRepository.findByEmail(currentUserProvider.getCurrentUsername()).orElseThrow())")
  //TODO: custom exception
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapDtoToPhotos")
  public abstract Profile requestDtoToEntity(ProfileRequestDto profileRequestDto);

  @Override
  @Mapping(source = "soul", target = "soul", qualifiedByName = "mapSoulToDto")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapPhotosToDto")
  public abstract ProfileResponseDto entityToResponseDto(Profile profile);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(target = "soul", expression = "java(soulRepository.findByEmail(currentUserProvider.getCurrentUsername()).orElseThrow())")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapDtoToPhotos")
  public abstract void updateEntityByRequestDto(ProfileRequestDto profileRequestDto, @MappingTarget Profile profile);

  @Named("mapDtoToPhotos")
  protected List<Photo> mapDtoToPhotos(List<String> photoUrl) {
    return photoUrl.stream()
      .map(s -> photoComponent.createIfNotFound(s))
      .toList();
  }

  @Named("mapSoulToDto")
  protected SoulResponseDto mapSoulToDto(Soul user) {
    return soulMapperService.entityToResponseDto(user);
  }

  @Named("mapPhotosToDto")
  protected List<PhotoResponseDto> mapPhotosToDto(List<Photo> photos) {
    return photos.stream()
      .map(photo -> photoMapperService.entityToResponseDto(photo))
      .toList();
  }
}
