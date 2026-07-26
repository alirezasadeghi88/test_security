package com.learn.security.service;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);

    RefreshToken verifyToken(String token);

    void revokeToken(String token);

    void revokeAll(User user);
}
