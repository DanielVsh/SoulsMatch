package com.danielvishnievskyi.soulsmatch.util.auth;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.model.entity.Token;
import com.danielvishnievskyi.soulsmatch.model.enums.TokenType;
import com.danielvishnievskyi.soulsmatch.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceUtilImpl implements AuthenticationServiceUtil{

  private final TokenRepository tokenRepository;

  @Override
  public void saveSoulToken(Soul soul, String jwtToken) {
    var token = Token.builder()
      .soul(soul)
      .token(jwtToken)
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();
    tokenRepository.save(token);
  }

  @Override
  public void deleteAllSoulTokens(Soul soul) {
    var validSoulTokens = tokenRepository.findAllValidTokenBySoul(soul.getId());
    tokenRepository.deleteAll(validSoulTokens);
  }

}
