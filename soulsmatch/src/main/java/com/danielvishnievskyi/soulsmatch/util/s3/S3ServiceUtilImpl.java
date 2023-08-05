package com.danielvishnievskyi.soulsmatch.util.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3ServiceUtilImpl implements S3ServiceUtil {

  private final S3Client s3Client;

  @Override
  public void putObject(String bucketName, String key, byte[] file) {
    PutObjectRequest objectRequest = PutObjectRequest.builder()
      .bucket(bucketName)
      .key(key)
      .build();
    s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
  }

  @Override
  public byte[] getObject(String bucketName, String key) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
      .bucket(bucketName)
      .key(key)
      .build();

    try {
      return s3Client.getObject(getObjectRequest).readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteObject(String bucketName, String key) {
    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
      .bucket(bucketName)
      .key(key)
      .build();

    s3Client.deleteObject(deleteObjectRequest);
  }
}
