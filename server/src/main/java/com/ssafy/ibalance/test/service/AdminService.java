package com.ssafy.ibalance.test.service;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.child.repository.childAllergy.ChildAllergyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final ChildAllergyRepository childAllergyRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;

    public void refreshAllChildAllergy() {
        List<ChildAllergy> allChildAllergy = childAllergyRepository.findAll();

        Map<Child, List<ChildAllergy>> collect = allChildAllergy.stream()
                .collect(Collectors.groupingBy(ChildAllergy::getChild));

        List<RedisChildAllergy> allRedisChildAllergy = collect.entrySet().stream()
                .map(entry -> RedisChildAllergy.builder()
                        .childId(entry.getKey().getId())
                        .memberId(entry.getKey().getMember().getId())
                        .childAllergyId(entry.getValue().stream().map(ChildAllergy::getId).toList())
                        .build()
                ).toList();


        for (RedisChildAllergy childAllergy : allRedisChildAllergy) {
            log.info("child Id : {}", childAllergy.getChildId());
        }

        redisChildAllergyRepository.saveAll(allRedisChildAllergy);
    }
}
