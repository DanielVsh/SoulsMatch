package com.danielvishnievskyi.soulsmatch.repository;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoulRepository extends JpaRepository<Soul, Long> {
  Optional<Soul> findByEmail(String email);
}
