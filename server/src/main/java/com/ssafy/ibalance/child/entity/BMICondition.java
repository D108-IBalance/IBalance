package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.child.type.Condition;
import com.ssafy.ibalance.child.type.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class BMICondition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int growMonth;

    @Enumerated(EnumType.STRING)
    @Column(length = 11, nullable = false)
    private Condition condition;

    @Column(nullable = false)
    private double standard;
}
