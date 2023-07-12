package com.danielvishnievskyi.soulsmatch.util.swipe;

import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;

public interface SwipeServiceUtil {
  Swipe createIfNotFound(String username);
  void updateSwipedProfilesBySoul(String username, Long swipedProfileId);
}
