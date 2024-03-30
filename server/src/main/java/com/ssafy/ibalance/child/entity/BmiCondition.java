package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.child.type.WeightCondition;
import com.ssafy.ibalance.child.type.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class BmiCondition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int growMonth;

    @Enumerated(EnumType.STRING)
    @Column(length = 11, nullable = false)
    private WeightCondition weightCondition;

    @Column(nullable = false)
    private double standard;
}
