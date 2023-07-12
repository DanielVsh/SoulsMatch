package com.danielvishnievskyi.soulsmatch.util.like;

import com.danielvishnievskyi.soulsmatch.model.entity.Like;

public interface LikeServiceUtil {
  Like createIfNotFound(String username);

  void likeProfile(String username, Long profileId);

  void deleteLikeProfile(String username, Long profileId);
}
