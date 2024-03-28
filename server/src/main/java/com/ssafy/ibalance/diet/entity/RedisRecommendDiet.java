package com.ssafy.ibalance.diet.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Builder
@Getter
@RedisHash(value = "recommendDiet")
public class RedisRecommendDiet {

    @Id
    private String id;  // {childId}_{dietDay}

    private List<List<String>> dietList;
}
