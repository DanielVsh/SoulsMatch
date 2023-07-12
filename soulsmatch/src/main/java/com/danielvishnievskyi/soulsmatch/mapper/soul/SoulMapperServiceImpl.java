package com.danielvishnievskyi.soulsmatch.mapper.soul;

import com.danielvishnievskyi.soulsmatch.mapper.location.LocationMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.mapper.photo.PhotoMapperServiceImpl;
import com.danielvishnievskyi.soulsmatch.model.dto.request.LocationRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.util.location.LocationServiceUtil;
import com.danielvishnievskyi.soulsmatch.util.photo.PhotoServiceUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public abstract class SoulMapperServiceImpl implements SoulMapperService {

  @Autowired
  protected LocationMapperServiceImpl locationMapperService;

  @Autowired
  protected PhotoMapperServiceImpl photoMapperService;

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected PhotoServiceUtil photoServiceUtil;

  @Autowired
  protected LocationServiceUtil locationServiceUtil;

  @Override
  @Mapping(source = "location", target = "location", qualifiedByName = "mapRequestDtoToLocation")
  @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
  @Mapping(source = "password", target = "password", qualifiedByName = "mapPasswordToBCrypt")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapRequestDtoToPhotos")
  public abstract Soul requestDtoToEntity(SoulRequestDto soulRequestDto);

  @Override
  @Mapping(source = "location", target = "location", qualifiedByName = "mapLocationToResponseDto")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapPhotosToResponseDto")
  public abstract SoulResponseDto entityToResponseDto(Soul soul);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "location", target = "location", qualifiedByName = "mapRequestDtoToLocation")
  @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
  @Mapping(source = "password", target = "password", qualifiedByName = "mapPasswordToBCrypt")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapRequestDtoToPhotos")
  public abstract void updateEntityByRequestDto(SoulRequestDto soulRequestDto, @MappingTarget Soul soul);

  @Named("mapPasswordToBCrypt")
  public String mapPasswordToBCrypt(String password) {
    return passwordEncoder.encode(password);
  }

  @Named("mapStringToLocalDate")
  public LocalDate mapStringToLocalDate(String birthDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(birthDate, formatter);
  }

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

  @Named("mapLocationToResponseDto")
  protected LocationResponseDto mapLocationToResponseDto(Location location) {
    return locationMapperService.entityToResponseDto(location);
  }

  @Named("mapPhotosToResponseDto")
  protected List<PhotoResponseDto> mapPhotosToResponseDto(List<Photo> photos) {
    return photos.stream()
      .map(photo -> photoMapperService.entityToResponseDto(photo))
      .toList();
  }
}
