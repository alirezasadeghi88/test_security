package com.learn.security.service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public RefreshToken createRefreshToken(User user) {

        repository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000)
        );

        refreshToken.setExpired(false);

        refreshToken.setRevoked(false);

        return repository.save(refreshToken);

    }

    @Override
    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new TokenException("Refresh token not found"));

        if (refreshToken.getRevoked()) {

            throw new TokenException("Refresh token revoked");

        }

        if (refreshToken.getExpired()) {

            throw new TokenException("Refresh token expired");

        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshToken.setExpired(true);

            repository.save(refreshToken);

            throw new TokenException("Refresh token expired");

        }

        return refreshToken;

    }

    @Override
    public void revokeToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new TokenException("Refresh token not found"));

        refreshToken.setRevoked(true);

        refreshToken.setRevokedAt(LocalDateTime.now());

        repository.save(refreshToken);

    }

    @Override
    public void revokeAll(User user) {

        repository.deleteByUser(user);

    }

}
