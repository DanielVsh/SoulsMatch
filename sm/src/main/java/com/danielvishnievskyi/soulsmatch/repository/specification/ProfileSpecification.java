package com.danielvishnievskyi.soulsmatch.repository.specification;

import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import com.danielvishnievskyi.soulsmatch.model.entity.Swipe;
import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProfileSpecification {

  public static Specification<Profile> withNotSwipedProfiles(String username) {
    return (root, query, criteriaBuilder) -> {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<Swipe> subqueryRoot = subquery.from(Swipe.class);
      Join<Swipe, Profile> swipedProfilesJoin = subqueryRoot.join("swipedProfiles");
      subquery.select(swipedProfilesJoin.get("id"))
        .where(criteriaBuilder.equal(subqueryRoot.get("soul").get("email"), username));

      return criteriaBuilder.not(root.get("id").in(subquery));
    };
  }

  public static Specification<Profile> withPreferredGenders(List<Gender> preferredGenders) {
    return (root, query, criteriaBuilder) -> root.get("soul").get("gender").in(preferredGenders);
  }

  public static Specification<Profile> withCountry(String country) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("soul").get("location").get("country").get("name"), country);
  }

  public static Specification<Profile> withDistance(double lat1, double lon1) {
    return (root, query, criteriaBuilder) -> {
      var lat2 = root.get("soul").get("location").get("latitude");
      var lon2 = root.get("soul").get("location").get("longitude");
      Expression<Double> distanceExpression = criteriaBuilder.function("getDistanceBetweenTwoPlacesByCoordinates", Double.class,
        criteriaBuilder.literal(lat1), criteriaBuilder.literal(lon1), lat2, lon2);
      query.orderBy(criteriaBuilder.asc(distanceExpression));
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    };
  }
}

