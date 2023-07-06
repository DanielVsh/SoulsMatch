package com.danielvishnievskyi.soulsmatch.repository;

import com.danielvishnievskyi.soulsmatch.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
  @Query(value = """
    select t from Token t inner join Soul u\s
    on t.soul.id = u.id\s
    where u.id = :id and (t.expired = false or t.revoked = false)\s
    """)
  List<Token> findAllValidTokenBySoul(Long id);

  Optional<Token> findByToken(String token);

}