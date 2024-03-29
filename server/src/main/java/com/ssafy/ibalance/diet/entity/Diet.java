package com.ssafy.ibalance.diet.entity;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.common.util.BaseTime;
import com.ssafy.ibalance.diet.type.MealTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Diet extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dietDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MealTime mealTime = MealTime.NONE;

    @Column
    private String diary;

    @Column(columnDefinition = "boolean default false")
    private boolean isReviewed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Child child;
}
