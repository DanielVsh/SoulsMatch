package com.danielvishnievskyi.soulsmatch.mapper;

import com.danielvishnievskyi.soulsmatch.component.photo.PhotoComponent;
import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.LocationResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.PhotoResponseDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.LocationRepository;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
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
public abstract class SoulMapperService implements MapperService<Soul, SoulRequestDto, SoulResponseDto> {

  @Autowired
  protected LocationRepository locationRepository;

  @Autowired
  protected PhotoRepository photoRepository;

  @Autowired
  protected LocationMapperService locationMapperService;

  @Autowired
  protected PhotoMapperService photoMapperService;

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected PhotoComponent photoComponent;

  @Override
  @Mapping(source = "location", target = "location", qualifiedByName = "mapDtoToLocation")
  @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
  @Mapping(source = "password", target = "password", qualifiedByName = "passwordToBCrypt")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapDtoToPhotos")
  public abstract Soul requestDtoToEntity(SoulRequestDto soulRequestDto);

  @Override
  @Mapping(source = "location", target = "location", qualifiedByName = "mapLocationToDto")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapPhotosToDto")
  public abstract SoulResponseDto entityToResponseDto(Soul soul);

  @Override
  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
  @Mapping(source = "location", target = "location", qualifiedByName = "mapDtoToLocation")
  @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "mapStringToLocalDate")
  @Mapping(source = "password", target = "password", qualifiedByName = "passwordToBCrypt")
  @Mapping(source = "photos", target = "photos", qualifiedByName = "mapDtoToPhotos")
  public abstract void updateEntityByRequestDto(SoulRequestDto soulRequestDto, @MappingTarget Soul soul);

  @Named("passwordToBCrypt")
  public String passwordToBCrypt(String password) {
    return passwordEncoder.encode(password);
  }

  @Named("mapStringToLocalDate")
  public LocalDate mapStringToLocalDate(String birthDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(birthDate, formatter);
  }

  @Named("mapDtoToPhotos")
  protected List<Photo> mapDtoToPhotos(List<String> photoUrl) {
    return photoUrl.stream()
      .map(s -> photoComponent.createIfNotFound(s))
      .toList();
  }

  @Named("mapDtoToLocation")
  protected Location mapDtoToLocation(Long locationId) {
    //TODO: throw the exception
    return locationRepository.findById(locationId).orElseThrow();
  }

  @Named("mapLocationToDto")
  protected LocationResponseDto mapLocationToDto(Location location) {
    return locationMapperService.entityToResponseDto(location);
  }

  @Named("mapPhotosToDto")
  protected List<PhotoResponseDto> mapPhotosToDto(List<Photo> photos) {
    return photos.stream()
      .map(photo -> photoMapperService.entityToResponseDto(photo))
      .toList();
  }
}
