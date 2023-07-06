package com.danielvishnievskyi.soulsmatch.component.swipe;

import com.danielvishnievskyi.soulsmatch.component.ComponentService;
import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;

public interface SwipeComponent extends ComponentService<Swipe, String> {
  void updateSwipedProfilesBySoul(String username, Long swipedProfileId);
}
