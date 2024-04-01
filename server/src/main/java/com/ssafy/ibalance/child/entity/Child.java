package com.ssafy.ibalance.child.entity;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.common.util.BaseTime;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicInsert
public class Child extends BaseTime {

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

    @Column(columnDefinition = "varchar(255) default '초기 이미지 URL'")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    public static Child ConvertDtoToEntity(RegistChildRequest registChildRequest, Member member) {

        return builder()
                .name(registChildRequest.getName())
                .birthDate(LocalDate.parse(registChildRequest.getBirthDate()))
                .gender(Gender.valueOf(registChildRequest.getGender()))
                .height(registChildRequest.getHeight())
                .weight(registChildRequest.getWeight())
                .imageUrl(registChildRequest.getImageUrl())
                .member(member)
                .build();
    }
}
