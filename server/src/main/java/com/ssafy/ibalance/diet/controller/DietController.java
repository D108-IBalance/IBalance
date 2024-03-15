package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.diet.dto.response.ChildDietResponse;
import com.ssafy.ibalance.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;

    @GetMapping("/{childId}")
    public List<ChildDietResponse> getChildDiet(@PathVariable Integer childId, @RequestParam LocalDate today) {
        return dietService.getChildDiet(childId, today);
    }
}
