package com.ssafy.ibalance.child.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Builder
@Getter
@RedisHash(value = "childAllergy")
public class RedisChildAllergy {

    @Id
    private Integer childId;

    private List<Long> childAllergyId;
}