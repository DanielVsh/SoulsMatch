package com.danielvishnievskyi.soulsmatch.util.photo;

import com.danielvishnievskyi.soulsmatch.model.entity.Photo;

public interface PhotoServiceUtil {
  Photo createIfNotFound(String url);
}
