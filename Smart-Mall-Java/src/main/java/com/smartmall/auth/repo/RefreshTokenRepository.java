package com.smartmall.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartmall.auth.entity.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
