package com.ssafy.ibalance.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Long> childAllergyTemplate;

    public void setChildAllergy(Integer key, List<Long> values) {

        childAllergyTemplate.opsForList().rightPushAll("child_allergy" + Integer.toString(key), values);
    }

    public List<Long> getChildAllergy(Integer key) {

        RedisOperations<String, Long> operations = childAllergyTemplate.opsForList().getOperations();
        return operations.opsForList().range("child_allergy" + Integer.toString(key), 0, -1);
    }

    public void deleteChildAllergy(Integer key) {
        childAllergyTemplate.delete("child_allergy" + Integer.toString(key));
    }
}
