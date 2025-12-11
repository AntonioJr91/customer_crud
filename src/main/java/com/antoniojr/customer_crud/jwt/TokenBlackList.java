package com.antoniojr.customer_crud.jwt;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TokenBlackList {
  private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

  public void revoke(String token) {
    blacklist.add(token);
  }

  public boolean isRevoked(String token) {
    return blacklist.contains(token);
  }
}