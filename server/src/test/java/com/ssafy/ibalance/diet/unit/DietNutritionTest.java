package com.ssafy.ibalance.diet.unit;

import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.diet.type.DietNutrition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DietNutritionTest {

    @Test
    void 연령_비만_영양정보_들고오기() {
        DietNutrition obesityNutrition = DietNutrition.getNutrition(6, Gender.MALE, true);
        DietNutrition normalNutrition = DietNutrition.getNutrition(6, Gender.MALE, false);

        assertThat(obesityNutrition.getCalorie()).isEqualTo((int) (normalNutrition.getCalorie() * 0.8));
    }
}
