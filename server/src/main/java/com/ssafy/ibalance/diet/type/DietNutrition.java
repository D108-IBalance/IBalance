package com.ssafy.ibalance.diet.type;

import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.diet.exception.NutritionServiceException;
import lombok.Getter;

@Getter
public enum DietNutrition {

    AGE_TWO(270, 39, 6, 5),
    AGE_TWO_OBESITY(obesityCalorie(AGE_TWO),
            AGE_TWO.carbonHydrate, AGE_TWO.protein, AGE_TWO.dietFiber),
    AGE_THREE_TO_FIVE(420, 39, 8, 6),
    AGE_THREE_TO_FIVE_OBESITY(obesityCalorie(AGE_THREE_TO_FIVE),
            AGE_THREE_TO_FIVE.carbonHydrate, AGE_THREE_TO_FIVE.protein, AGE_THREE_TO_FIVE.dietFiber),
    AGE_SIX_TO_EIGHT_BOY(510, 39, 11, 8),
    AGE_SIX_TO_EIGHT_BOY_OBESITY(obesityCalorie(AGE_SIX_TO_EIGHT_BOY),
            AGE_SIX_TO_EIGHT_BOY.carbonHydrate, AGE_SIX_TO_EIGHT_BOY.protein, AGE_SIX_TO_EIGHT_BOY.dietFiber),
    AGE_SIX_TO_EIGHT_GIRL(450, 39, 11, 6),
    AGE_SIX_TO_EIGHT_GIRL_OBESITY(obesityCalorie(AGE_SIX_TO_EIGHT_GIRL),
            AGE_SIX_TO_EIGHT_GIRL.carbonHydrate, AGE_SIX_TO_EIGHT_GIRL.protein, AGE_SIX_TO_EIGHT_GIRL.dietFiber);


    private int calorie;
    private int carbonHydrate;
    private int protein;
    private int dietFiber;

    DietNutrition(int calorie, int carbonHydrate, int protein, int dietFiber) {
        this.calorie = calorie;
        this.carbonHydrate = carbonHydrate;
        this.protein = protein;
        this.dietFiber = dietFiber;
    }

    public static DietNutrition getNutrition(int age, Gender gender, boolean obesity) {
        DietNutrition nutrition = getNormalNutrition(age, gender);
        if(obesity) {
            return DietNutrition.valueOf(getObesityName(nutrition));
        }
        return nutrition;
    }

    private static DietNutrition getNormalNutrition(int age, Gender gender) {
        return switch (age) {
            case 2 -> AGE_TWO;
            case 3, 4, 5 -> AGE_THREE_TO_FIVE;
            case 6, 7, 8 -> {
                if(Gender.MALE.equals(gender)) {
                    yield AGE_SIX_TO_EIGHT_BOY;
                }
                yield AGE_SIX_TO_EIGHT_GIRL;
            }
            default -> throw new NutritionServiceException("현재 서비스되고 있지 않은 연령입니다.");
        };
    }

    private static int obesityCalorie(DietNutrition nutrition) {
        return (int) (nutrition.calorie * 0.8);
    }

    private static String getObesityName(DietNutrition nutrition) {
        return String.format("%s_OBESITY", nutrition.name());
    }
}
