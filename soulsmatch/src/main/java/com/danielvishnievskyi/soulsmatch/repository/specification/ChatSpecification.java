package com.danielvishnievskyi.soulsmatch.repository.specification;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.entity.chat.Chat;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ChatSpecification {
  public static Specification<Chat> findAllBySoulIn(String username) {
    return (root, query, criteriaBuilder) -> {
      Join<Chat, Soul> join = root.join("participants");
      return criteriaBuilder.equal(join.get("username"), username);
    };
  }
}