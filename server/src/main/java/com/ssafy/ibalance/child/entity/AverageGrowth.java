package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.child.type.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AverageGrowth {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;
}
