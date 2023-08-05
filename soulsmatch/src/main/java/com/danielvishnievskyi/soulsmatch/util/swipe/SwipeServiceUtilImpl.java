package com.danielvishnievskyi.soulsmatch.util.swipe;

import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.repository.SwipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class SwipeServiceUtilImpl implements SwipeServiceUtil {
  private final SwipeRepository swipeRepository;
  private final SoulRepository soulRepository;
  private final ProfileRepository profileRepository;

  @Override
  public Swipe createIfNotFound(String username) {
    return swipeRepository.findBySoulUsername(username)
      .orElseGet(() -> swipeRepository.save(
        new Swipe(
          null,
          soulRepository.findByUsername(username).orElseThrow(),//TODO: custom exception
          new HashSet<>())
      ));
  }

  @Override
  public void updateSwipedProfilesBySoul(String username, Long swipedProfileId) {
    Swipe swipe = createIfNotFound(username);

    swipe.getSwipedProfiles().add(profileRepository.findById(swipedProfileId).orElseThrow()); //TODO: custom exception

    swipeRepository.save(swipe);
  }
}
