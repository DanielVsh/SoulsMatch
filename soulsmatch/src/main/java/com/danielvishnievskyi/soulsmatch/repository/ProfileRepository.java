package com.danielvishnievskyi.soulsmatch.repository;

import com.danielvishnievskyi.soulsmatch.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
  Optional<Profile> findBySoulId(Long soulId);

  Optional<Profile> findBySoulEmail(String username);
}