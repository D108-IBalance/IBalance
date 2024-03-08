package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.child.dto.request.RegistChildRequestDto;
import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.member.entity.Member;
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
public class Child {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    public static Child from(RegistChildRequestDto registChildRequestDto, Member member) {

        return builder()
                .name(registChildRequestDto.getName())
                .birthDate(registChildRequestDto.getBirthDate())
                .gender(registChildRequestDto.getGender())
                .height(registChildRequestDto.getHeight())
                .weight(registChildRequestDto.getWeight())
                .imageUrl(registChildRequestDto.getImageUrl())
                .member(member)
                .build();
    }
}
