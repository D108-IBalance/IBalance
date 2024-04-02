package com.ssafy.ibalance.diet.entity;

import com.ssafy.ibalance.diet.dto.RedisDietDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Builder
@Getter
@RedisHash(value = "recommendDiet", timeToLive = 1800)
public class RedisRecommendDiet {

    @Id
    private String id;  // {childId}_{dietDay}

    private List<RedisDietDto> dietList;
}
