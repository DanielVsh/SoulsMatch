package com.danielvishnievskyi.soulsmatch.util.photo;

import com.danielvishnievskyi.soulsmatch.model.dto.request.PhotoRequestDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Photo;

import java.util.List;
import java.util.UUID;

public interface PhotoServiceUtil {
  List<Photo> processPhotoRequestAndUpdatePhotos(List<PhotoRequestDto> photoRequestDtos, String username);

  Photo createPhotoAndUploadToS3Bucket(byte[] photo, String username);

  void deletePhotoAndRemoveFromS3Bucket(UUID id);
}
