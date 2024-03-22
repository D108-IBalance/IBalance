package com.ssafy.ibalance.member.repository;

import com.ssafy.ibalance.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    boolean deleteByRefreshToken(String refreshToken);
}
