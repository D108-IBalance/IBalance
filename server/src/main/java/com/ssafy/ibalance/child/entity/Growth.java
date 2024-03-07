package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Growth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double height;

    @Column
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Child child;
}
