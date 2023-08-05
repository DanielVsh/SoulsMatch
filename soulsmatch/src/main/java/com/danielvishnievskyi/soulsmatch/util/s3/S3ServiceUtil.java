package com.danielvishnievskyi.soulsmatch.util.s3;

public interface S3ServiceUtil {
  void putObject(String bucketName, String key, byte[] file);
  byte[] getObject(String bucketName, String key);
  void deleteObject(String bucketName, String key);
}
