package com.ssafy.ibalance.child.service;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.*;
import com.ssafy.ibalance.child.entity.Allergy;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.AllergyRepository;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.common.util.RedisUtil;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.child.dto.response.ChildDietResponse;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final DietRepository dietRepository;
    private final RedisUtil redisUtil;

    public List<ChildListResponse> getChildList(Integer memberId) {

        List<Child> children = childRepository.findAllByMemberId(memberId);
        return children.stream().map(ChildListResponse::ConvertEntityToDto).toList();
    }

    public RegistChildResponse registChild(RegistChildRequest registChildRequest, Member member) {

        Child child = saveChild(registChildRequest, member);
        saveGrowth(child);
        List<Long> childAllergyList = saveChildAllergy(registChildRequest, child);
        redisUtil.setChildAllergy(child.getId(), childAllergyList);
        return RegistChildResponse.convertEntityToDto(child);
    }

    public DeleteChildResponse deleteChild(Integer childId) {
        Child child = childRepository.findById(childId).orElseThrow(IllegalArgumentException::new);
        childRepository.delete(child);
        redisUtil.deleteChildAllergy(childId);
        return DeleteChildResponse.ConvertEntityToDto(child);
    }

    private Child saveChild(RegistChildRequest registChildRequest, Member member) {

        return Child.ConvertDtoToEntity(registChildRequest, member);
    }

    private void saveGrowth(Child child) {

        Growth growth = Growth.builder()
                .height(child.getHeight())
                .weight(child.getWeight())
                .child(child)
                .build();

        growthRepository.save(growth);
    }

    private List<Long> saveChildAllergy(RegistChildRequest registChildRequest, Child child) {

        List<Long> childAllergyList = new ArrayList<>();

        for(Integer i : registChildRequest.getHaveAllergies()) {
            Allergy allergy = allergyRepository.findById(i).orElseThrow(IllegalArgumentException::new);
            ChildAllergy childAllergy = ChildAllergy.builder()
                    .child(child)
                    .allergy(allergy)
                    .build();
            childAllergyList.add(childAllergyRepository.save(childAllergy).getId());
        }

        return childAllergyList;
    }

    public ChildDietResponse getMain(Integer childId, LocalDate date) {
        return ChildDietResponse.builder()
                .childDetailResponse(getChildDetail(childId))
                .dietList(dietRepository.getDietByDate(childId, date)).build();
    }

    public ChildDetailResponse getChildDetail(Integer childId) {
        Growth growth = growthRepository
                .findTopByChildIdOrderByCreatedTimeDesc(childId)
                .orElseThrow(() -> new ChildNotFoundException("해당하는 자녀가 없습니다."));

        return ChildDetailResponse.convertEntityToDto(growth);
    }

    public Page<GrowthResponse> getGrowthList(Integer childId, Pageable pageable) {
        return growthRepository.findByChildIdOrderByIdDesc(childId, pageable)
                .map(GrowthResponse::ConvertEntityToDto);
    }
}
