package com.danielvishnievskyi.soulsmatch.util.swipe;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import com.danielvishnievskyi.soulsmatch.repository.SwipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SwipeServiceUtilImpl implements SwipeServiceUtil {
  private final SwipeRepository swipeRepository;
  private final SoulRepository soulRepository;
  private final ProfileRepository profileRepository;

  @Override
  public Swipe createIfNotFound(String username) {
    return swipeRepository.findBySoulEmail(username)
      .orElseGet(() -> {
        Soul soul = soulRepository.findByEmail(username)
          .orElseThrow(); //TODO: custom exception
        return swipeRepository.save(new Swipe(null, soul, Set.of()));
      });
  }

  @Override
  public void updateSwipedProfilesBySoul(String username, Long swipedProfileId) {
    Swipe swipe = createIfNotFound(username);

    swipe.getSwipedProfiles().add(profileRepository.findById(swipedProfileId).orElseThrow()); //TODO: custom exception

    swipeRepository.save(swipe);
  }
}
