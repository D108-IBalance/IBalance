package com.ssafy.ibalance.child;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChildSteps {

    public static String name = "SSAFY";
    public static String birthDate = "2020-05-05";
    public static String gender = "MALE";
    public static Double height = 123.4;
    public static Double weight = 43.2;
    public static List<Integer> haveAllergies = List.of(1,2);
    public static List<Integer> wrongAllergies = List.of(1,20);

    public RegistChildRequest 아이정보_생성(){
        return RegistChildRequest.builder()
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .height(height)
                .weight(weight)
                .haveAllergies(haveAllergies)
                .build();
    }

    public RegistChildRequest 아이정보_잘못_생성(){
        return RegistChildRequest.builder()
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .height(height)
                .weight(weight)
                .haveAllergies(wrongAllergies)
                .build();
    }
}
