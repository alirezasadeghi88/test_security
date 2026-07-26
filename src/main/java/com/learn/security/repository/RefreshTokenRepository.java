package com.learn.security.repository;

import com.learn.security.model.RefreshToken;
import com.learn.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token) ;

    void deleteByUser(User user);
}
