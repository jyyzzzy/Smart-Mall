package com.smartmall.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartmall.auth.entity.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOpenid(String openid);
    Optional<User> findByUnionid(String unionid);
}
