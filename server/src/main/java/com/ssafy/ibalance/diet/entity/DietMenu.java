package com.ssafy.ibalance.diet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DietMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer menuId;

    @Column
    private Float score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Diet diet;
}
