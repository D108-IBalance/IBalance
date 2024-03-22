package com.ssafy.ibalance.diet.entity;

import com.ssafy.ibalance.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DietMaterial {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private boolean picky;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Diet diet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Material material;

}