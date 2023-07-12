package com.danielvishnievskyi.soulsmatch.util.auth;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;

public interface AuthenticationServiceUtil {

  void saveSoulToken(Soul soul, String jwtToken);

  void deleteAllSoulTokens(Soul soul);

}
