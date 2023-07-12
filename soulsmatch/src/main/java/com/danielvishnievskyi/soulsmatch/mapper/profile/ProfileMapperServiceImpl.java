package com.danielvishnievskyi.soulsmatch.mapper.profile;

import com.danielvishnievskyi.soulsmatch.mapper.photo.PhotoMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.mapper.soul.SoulMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.ProfileRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.ProfileResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.util.auth.CurrentUserProvider;
import com.danielvishnievskyi.soulsmatch.util.location.LocationServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.photo.PhotoServiceUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class ProfileMapperServiceImpl implements ProfileMapperService {

  @Autowired
  protected SoulRepository soulRepository;

  @Autowired
  protected SoulMapperServiceImpl soulMapperService;

  @Autowired
  protected PhotoMapperServiceImpl photoMapperService;

  @Autowired
  protected PhotoServiceUtil photoServiceUtil;

  @Autowired
  protected CurrentUserProvider currentUserProvider;

  @Autowired
  protected LocationServiceUtil locationServiceUtil;

  @Override
  @Mapping(target = "soul", expression = "java(soulRepository.findByEmail(currentUserProvider.getCurrentUsername()).orElseThrow())")
  //TODO: custom exception
  @Mapping(target = "location", source = "location", qualifiedByName = "mapRequestDtoToLocation")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapRequestDtoToPhotos")
  public abstract Profile requestDtoToEntity(ProfileRequestDto profileRequestDto);

  @Override
  @Mapping(source = "soul", target = "soul", qualifiedByName = "mapSoulToResponseDto")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapPhotosToResponseDto")
  public abstract ProfileResponseDto entityToResponseDto(Profile profile);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(target = "soul", expression = "java(soulRepository.findByEmail(currentUserProvider.getCurrentUsername()).orElseThrow())")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapRequestDtoToPhotos")
  public abstract void updateEntityByRequestDto(ProfileRequestDto profileRequestDto, @MappingTarget Profile profile);

  @Named("mapRequestDtoToPhotos")
  protected List<Photo> mapRequestDtoToPhotos(List<String> photoUrl) {
    return photoUrl.stream()
      .map(url -> photoServiceUtil.createIfNotFound(url))
      .toList();
  }

  @Named("mapRequestDtoToLocation")
  protected Location mapRequestDtoToLocation(LocationRequestDto locationRequestDto) {
    return locationServiceUtil.createIfNotFound(
      locationRequestDto.name(),
      locationRequestDto.latitude(),
      locationRequestDto.longitude()
    );
  }

  @Named("mapSoulToResponseDto")
  protected SoulResponseDto mapSoulToResponseDto(Soul user) {
    return soulMapperService.entityToResponseDto(user);
  }

  @Named("mapPhotosToResponseDto")
  protected List<PhotoResponseDto> mapPhotosToResponseDto(List<Photo> photos) {
    return photos.stream()
      .map(photo -> photoMapperService.entityToResponseDto(photo))
      .toList();
  }
}
