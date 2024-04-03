package com.ssafy.ibalance.child.dto.request;

import com.ssafy.ibalance.child.dto.annotation.CheckAllergies;
import com.ssafy.ibalance.child.dto.annotation.CheckDouble;
import com.ssafy.ibalance.child.dto.annotation.BeforeDateFormat;
import com.ssafy.ibalance.child.dto.annotation.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class RegistChildRequest {

    @Size(max = 20, min = 1, message = "아이 이름은 최대 20자까지 입력할 수 있습니다.")
    @NotNull(message = "아이 이름은 필수값입니다.")
    @NotEmpty(message = "아이 이름은 필수값입니다.")
    private String name;

    @NotNull(message = "생년월일을 입력해주세요")
    @BeforeDateFormat
    private String birthDate;

    @NotNull(message = "성별을 입력해 주세요")
    @Gender
    private String gender;

    @NotNull(message = "키를 입력해주세요")
    @CheckDouble
    private Double height;

    @NotNull(message = "몸무게를 입력해주세요")
    @CheckDouble
    private Double weight;

    private String imageUrl;

    @NotNull(message = "알러지 정보를 입력해 주세요")
    @CheckAllergies
    private List<Integer> haveAllergies;
}
