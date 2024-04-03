package com.ssafy.ibalance.diet.repository.diet;

import com.ssafy.ibalance.diet.entity.RedisDoNotRecommendMenuId;
import org.springframework.data.repository.CrudRepository;

public interface RedisDoNotRecommendRepository extends CrudRepository<RedisDoNotRecommendMenuId, Integer> {
}
