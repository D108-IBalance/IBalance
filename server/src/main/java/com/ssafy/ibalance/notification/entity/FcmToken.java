package com.ssafy.ibalance.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "fcm", timeToLive = 5_184_000)
public class FcmToken {

    @Id
    private Integer memberId;

    private String fcmToken;
}
