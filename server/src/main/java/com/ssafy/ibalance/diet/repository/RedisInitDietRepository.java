package com.ssafy.ibalance.diet.repository;

import com.ssafy.ibalance.diet.entity.RedisRecommendDiet;
import org.springframework.data.repository.CrudRepository;

public interface RedisInitDietRepository extends CrudRepository<RedisRecommendDiet, String> {
}
