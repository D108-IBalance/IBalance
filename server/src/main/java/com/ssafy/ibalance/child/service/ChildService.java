package com.ssafy.ibalance.child.service;

import com.ssafy.ibalance.child.dto.request.RegistChildRequestDto;
import com.ssafy.ibalance.child.dto.response.ChildListResponseDto;
import com.ssafy.ibalance.child.entity.Allergy;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.repository.AllergyRepository;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.common.util.RedisUtil;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final GrowthRepository growthRepository;
    private final AllergyRepository allergyRepository;
    private final ChildAllergyRepository childAllergyRepository;
    private final RedisUtil redisUtil;

    // MemberRepository 제작 완료 후 주석 제거
    // private final MemberRepository memberRepository;

    public List<ChildListResponseDto> getChildList(Integer memberId) {

        List<Child> children = childRepository.findAllByMemberId(memberId);
        return children.stream().map(ChildListResponseDto::of).toList();
    }

    public void registChild(RegistChildRequestDto registChildRequestDto) {

        Child child = saveChild(registChildRequestDto);
        saveGrowth(child);
        List<Long> childAllergyList = saveChildAllergy(registChildRequestDto, child);
        redisUtil.setChildAllergy(child.getId(), childAllergyList);
    }

    public void deleteChild(Integer childId) {
        childRepository.deleteById(childId);
        redisUtil.deleteChildAllergy(childId);
    }


    public Child saveChild(RegistChildRequestDto registChildRequestDto) {

        // 자녀 프로필 초기 이미지 설정
        registChildRequestDto.setImageUrl("이미지url");

        // MemberRepository 제작 완료 후 주석 제거
        // memberId 구하는 메서드 필요
        // Member member = memberRepository.finById(Integer memberId)

        // 임시 member
        Member member = new Member();
        member.setId(1);

        Child child = Child.from(registChildRequestDto, member);
        return childRepository.save(child);
    }

    public void saveGrowth(Child child) {

        Growth growth = Growth.builder()
                .height(child.getHeight())
                .weight(child.getWeight())
                .child(child)
                .build();

        growthRepository.save(growth);
    }

    public List<Long> saveChildAllergy(RegistChildRequestDto registChildRequestDto, Child child) {

        List<Long> childAllergyList = new ArrayList<>();

        for(Integer i : registChildRequestDto.getHaveAllergies()) {
            Allergy allergy = allergyRepository.findById(i).orElseThrow(IllegalArgumentException::new);
            ChildAllergy childAllergy = ChildAllergy.builder()
                    .child(child)
                    .allergy(allergy)
                    .build();
            childAllergyList.add(childAllergyRepository.save(childAllergy).getId());
        }

        return childAllergyList;
    }
}
