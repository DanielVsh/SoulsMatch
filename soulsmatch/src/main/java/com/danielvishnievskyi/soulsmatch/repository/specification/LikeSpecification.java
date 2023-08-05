package com.danielvishnievskyi.soulsmatch.repository.specification;

import com.danielvishnievskyi.soulsmatch.model.entity.Like;
import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LikeSpecification {

  public static Specification<Profile> findRequestedProfilesByLikedSoulUsername(String username) {
    return (root, query, criteriaBuilder) -> {
      Root<Like> subqueryRoot = query.from(Like.class);
      Join<Like, Profile> likedProfilesJoin = subqueryRoot.join("likedProfiles");
      Join<Like, Soul> soulJoin = likedProfilesJoin.join("soul");
      Join<Profile, Soul> soulProfileJoin = subqueryRoot.join("soul");

      Predicate usernamePredicate = criteriaBuilder.equal(soulJoin.get("username"), username);

      return criteriaBuilder.and(usernamePredicate, criteriaBuilder.equal(soulProfileJoin, root.get("soul")));
    };
  }
}
