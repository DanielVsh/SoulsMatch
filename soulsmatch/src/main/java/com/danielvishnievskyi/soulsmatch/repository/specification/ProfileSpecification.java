package com.danielvishnievskyi.soulsmatch.repository.specification;

import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;
import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProfileSpecification {

  public static Specification<Profile> withNotSwipedProfiles(String username) {
    return (root, query, criteriaBuilder) -> {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<Swipe> subqueryRoot = subquery.from(Swipe.class);
      Join<Swipe, Profile> swipedProfilesJoin = subqueryRoot.join("swipedProfiles");
      subquery.select(swipedProfilesJoin.get("id"))
        .where(criteriaBuilder.equal(subqueryRoot.get("soul").get("username"), username));

      return criteriaBuilder.and(
        criteriaBuilder.not(root.get("id").in(subquery)),
        criteriaBuilder.notEqual(root.get("soul").get("username"), username)
      );
    };
  }

  public static Specification<Profile> withPreferredGenders(List<Gender> preferredGenders) {
    return (root, query, criteriaBuilder) -> root.get("soul").get("gender").in(preferredGenders);
  }

  public static Specification<Profile> withDistance(double lat1, double lon1) {
    return (root, query, criteriaBuilder) -> {
      Expression<Double> lat2 = root.get("location").get("latitude");
      Expression<Double> lon2 = root.get("location").get("longitude");
      Expression<Double> distanceExpression = criteriaBuilder.function(
        "getDistanceBetweenTwoPlacesByCoordinates",
        Double.class,
        criteriaBuilder.literal(lat1), criteriaBuilder.literal(lon1),
        lat2, lon2
      );
      query.orderBy(criteriaBuilder.asc(distanceExpression));
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    };
  }
}

