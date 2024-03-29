package com.ssafy.ibalance.diet.type;

public enum MealTime {
    BREAKFAST("아침"),
    LUNCH("점심"),
    DINNER("저녁"),
    NONE("선택안함");


    private String koreanName;

    MealTime(String koreanName) {
        this.koreanName = koreanName;
    }
}
