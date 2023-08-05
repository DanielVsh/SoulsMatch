package com.danielvishnievskyi.soulsmatch.util.photo;

import com.danielvishnievskyi.soulsmatch.config.s3.S3Buckets;
import com.danielvishnievskyi.soulsmatch.exception.photo.PhotoNotFoundException;
import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
import com.danielvishnievskyi.soulsmatch.util.s3.S3ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PhotoServiceUtilImpl implements PhotoServiceUtil {

  private final PhotoRepository photoRepository;
  private final S3ServiceUtil s3ServiceUtil;
  private final S3Buckets s3Buckets;

  @Override
  public List<Photo> processPhotoRequestAndUpdatePhotos(List<PhotoRequestDto> photoRequestDtos, String username) {
    return photoRequestDtos.stream()
      .map(photoRequestDto -> processPhotoRequest(photoRequestDto, username))
      .collect(Collectors.toList());
  }

  @Override
  public Photo createPhotoAndUploadToS3Bucket(byte[] photo, String username) {
    UUID photoId = UUID.randomUUID();
    String key = "image/%s/%s".formatted(username, photoId.toString());
    String bucket = s3Buckets.getCustomer();
    s3ServiceUtil.putObject(bucket, key, photo);
    return photoRepository.save(new Photo(photoId, key, bucket));
  }

  @Override
  public void deletePhotoAndRemoveFromS3Bucket(UUID id) {
    Photo photo = findPhotoOrElseThrow(id);
    s3ServiceUtil.deleteObject(photo.getBucket(), photo.getKey());
    photoRepository.deleteById(id);
  }

  private Photo processPhotoRequest(PhotoRequestDto photoRequestDto, String username) {
    return switch (photoRequestDto.getAction()) {
      case CREATE -> createPhoto(photoRequestDto, username);
      case DELETE -> deletePhoto(photoRequestDto);
    };
  }

  private Photo createPhoto(PhotoRequestDto photoRequestDto, String username) {
    try {
      return this.createPhotoAndUploadToS3Bucket(
        photoRequestDto.getPhoto().getBytes(),
        username
      );
    } catch (IOException e) {
      throw new RuntimeException("Failed to create photo: " + e.getMessage(), e);
    }
  }

  private Photo deletePhoto(PhotoRequestDto photoRequestDto) {
    UUID photoId = photoRequestDto.getId();
    this.deletePhotoAndRemoveFromS3Bucket(photoId);
    return null;
  }

  private Photo findPhotoOrElseThrow(UUID id) {
    return photoRepository.findById(id)
      .orElseThrow(() -> new PhotoNotFoundException("Photo not found with id: " + id));
  }
}
