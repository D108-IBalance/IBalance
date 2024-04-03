package com.ssafy.ibalance.notification.repository;

import com.ssafy.ibalance.notification.entity.FcmToken;
import org.springframework.data.repository.CrudRepository;

public interface FcmTokenRedisRepository extends CrudRepository<FcmToken, Integer> {
}
