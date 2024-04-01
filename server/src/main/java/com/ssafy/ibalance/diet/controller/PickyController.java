package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.diet.dto.annotation.CheckPeriodUnit;
import com.ssafy.ibalance.diet.dto.response.picky.PickyRecipe;
import com.ssafy.ibalance.diet.dto.response.picky.SimplePickyResponse;
import com.ssafy.ibalance.diet.service.PickyService;
import com.ssafy.ibalance.diet.type.PeriodUnit;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/picky")
@RequiredArgsConstructor
@Validated
public class PickyController {

    private final PickyService pickyService;

    @GetMapping("/{childId}")
    public List<SimplePickyResponse> getPickyOfChild(@AuthenticationPrincipal Member member,
                                                     @PathVariable @Min(value = 1, message = "아이 아이디는 1 이상의 숫자로 입력해야 합니다.") Integer childId,
                                                     @RequestParam @CheckPeriodUnit String limit) {

        return pickyService.getPickyResult(member, childId, PeriodUnit.valueOf(limit));
    }

    @GetMapping("/solution/{childId}")
    public List<PickyRecipe> getSolutionRecipeList(@AuthenticationPrincipal Member member,
                                                   @PathVariable @Min(value = 1, message = "아이 아이디는 1 이상의 숫자로 입력해야 합니다.") Integer childId,
                                                   @RequestParam String material,
                                                   @RequestParam @Min(value = 1, message = "가져올 갯수는 1 이상의 숫자로 입력해야 합니다.") Integer offset,
                                                   @RequestParam(required = false) String lastId) {
        return pickyService.getSolutionRecipeList(member, childId, material, offset, lastId);
    }

    @GetMapping("/detail/{material}/{recipeId}")
    public PickyRecipe getOneDetailRecipe(@PathVariable String material, @PathVariable String recipeId) {
        return pickyService.getOneDetailRecipe(material, recipeId);
    }
}
