package com.danielvishnievskyi.soulsmatch.util.like;

import com.danielvishnievskyi.soulsmatch.model.entity.Like;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.repository.LikeRepository;
import com.danielvishnievskyi.soulsmatch.repository.ProfileRepository;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeServiceUtilImpl implements LikeServiceUtil {

  private final LikeRepository likeRepository;
  private final SoulRepository soulRepository;
  private final ProfileRepository profileRepository;

  @Override
  public Like createIfNotFound(String username) {
    return likeRepository.findBySoulEmail(username)
      .orElseGet(() -> likeRepository.save(
          new Like(
            null,
            soulRepository.findByEmail(username).orElseThrow(), //TODO: custom exception
            Set.of()
          )
        ));
  }

  @Override
  public void likeProfile(String username, Long profileId) {
    Profile profile = profileRepository.findById(profileId).orElseThrow();//TODO: custom exception

    Like like = createIfNotFound(username);
    like.getLikedProfiles().add(profile);
    likeRepository.save(like);
  }

  @Override
  public void deleteLikeProfile(String username, Long profileId) {
    Profile profile = profileRepository.findById(profileId).orElseThrow();

    Like like = createIfNotFound(username);
    like.getLikedProfiles().remove(profile);
    likeRepository.save(like);
  }
}
