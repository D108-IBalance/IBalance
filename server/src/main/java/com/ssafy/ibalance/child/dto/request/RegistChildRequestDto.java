package com.ssafy.ibalance.child.dto.request;

import com.ssafy.ibalance.child.type.Gender;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class RegistChildRequestDto {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Gender gender;
    private double height;
    private double weight;
    private String imageUrl;
    private List<Integer> haveAllergies;

}
