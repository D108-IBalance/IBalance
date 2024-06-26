package com.ssafy.ibalance.child.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Builder
@Getter
@Setter
@RedisHash(value = "childAllergy")
public class RedisChildAllergy {

    @Id
    private Integer childId;

    private Integer memberId;

    private List<Long> childAllergyId;
}
