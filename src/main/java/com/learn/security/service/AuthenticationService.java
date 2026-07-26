package com.learn.security.service;

import com.learn.security.dto.*;

public interface AuthenticationService {

    LoginResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
    void logout(LogoutRequest request);
}
