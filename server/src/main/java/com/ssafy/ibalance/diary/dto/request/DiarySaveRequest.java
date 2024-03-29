package com.ssafy.ibalance.diary.dto.request;


import com.ssafy.ibalance.diary.dto.annotation.CheckMenuRate;
import com.ssafy.ibalance.diary.dto.annotation.CheckPickyId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiarySaveRequest {

    @NotNull(message = "식단 아이디를 입력해 주세요.")
    @Min(value = 1, message = "식단 아이디는 1 이상의 정수를 입력해야 합니다.")
    private Long dietId;

    @NotNull(message = "일기 내용을 입력해 주세요")
    @NotBlank(message = "일기 내용은 1자 이상 입력해 주세요")
    private String content;

    @NotNull(message = "메뉴에 대한 별점을 입력해 주세요.")
    @CheckMenuRate
    private List<MenuRateRequest> menuRate;

    @NotNull(message = "편식한 재료 아이디를 입력해 주세요")
    @CheckPickyId
    private List<Long> pickyIdList;
}
