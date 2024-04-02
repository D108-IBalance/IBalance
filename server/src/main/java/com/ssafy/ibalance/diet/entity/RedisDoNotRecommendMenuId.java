package com.ssafy.ibalance.diet.entity;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Builder
@Getter
@RedisHash(value = "doNotRecommend")
public class RedisDoNotRecommendMenuId {

    @Id
    private Integer Id;

    private List<String> menuList;
}
