package com.ssafy.ibalance.child.util;

import com.ssafy.ibalance.child.entity.Allergy;
import com.ssafy.ibalance.child.repository.AllergyRepository;
import com.ssafy.ibalance.common.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AllergyTestUtil extends TestBase {

    private static final List<String> allergyNameList = List.of("대두", "땅콩", "호두", "잣", "아황산류",
            "복숭아", "토마토", "난류", "우유", "새우", "고등어", "오징어", "게", "조개류", "돼지고기", "쇠고기"
                    , "닭고기", "메밀");

    @Autowired
    private AllergyRepository allergyRepository;

    public void 알러지정보_저장() {
        List<Allergy> allergyList = allergyNameList.stream().map(s -> Allergy.builder()
                        .allergyName(s)
                        .build())
                .toList();

        allergyRepository.saveAll(allergyList);
    }
}
