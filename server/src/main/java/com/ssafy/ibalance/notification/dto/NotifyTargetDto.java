package com.ssafy.ibalance.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotifyTargetDto {

    private Integer id;
    private LocalDateTime lastUpdate;
}
