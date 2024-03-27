package com.ssafy.ibalance.child.service;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.*;
import com.ssafy.ibalance.child.entity.*;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.child.exception.AllergyNotFoundException;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.*;
import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.common.util.S3Util;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.child.dto.response.ChildDietResponse;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    private final AverageGrowthRepository averageGrowthRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;
    private final S3Util s3Util;

    @Value("${default.img.boy}")
    private String defaultBoy;

    @Value("${default.img.girl}")
    private String defaultGirl;

    public List<ChildInfoResponse> getChildList(Integer memberId) {

        List<Child> children = childRepository.findAllByMemberId(memberId);
        return children.stream().map(ChildInfoResponse::ConvertEntityToDto).toList();
    }

    public RegistChildResponse registChild(RegistChildRequest registChildRequest, Member member) {

        Child child = saveChild(registChildRequest, member);
        saveGrowth(child);
        List<Long> childAllergyList = saveChildAllergy(registChildRequest, child);
        redisChildAllergyRepository.save(RedisChildAllergy.builder()
                .childId(child.getId())
                .childAllergyId(childAllergyList)
                .build());
        return RegistChildResponse.convertEntityToDto(child);
    }

    public DeleteChildResponse deleteChild(Member member, Integer childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("해당하는 아이를 찾을 수 없습니다."));

        if(!member.equals(child.getMember())) {
            throw new ChildAccessDeniedException("해당 아이 정보에 접근할 수 있는 권한이 없습니다.");
        }

        childRepository.delete(child);
        redisChildAllergyRepository.deleteById(childId);
        return DeleteChildResponse.ConvertEntityToDto(child);
    }

    private Child saveChild(RegistChildRequest registChildRequest, Member member) {
        Gender gender = Gender.valueOf(registChildRequest.getGender());

        if(gender.equals(Gender.MALE)) {
            registChildRequest.setImageUrl(defaultBoy);
        }
        else {
            registChildRequest.setImageUrl(defaultGirl);
        }

        return childRepository.save(Child.ConvertDtoToEntity(registChildRequest, member));
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

        List<Integer> allergyIdList = registChildRequest.getHaveAllergies();
        List<Allergy> allergies = allergyRepository.findAllById(allergyIdList);

        if(allergies.size() != allergyIdList.size()) {
            throw new AllergyNotFoundException("리스트에 있는 알러지 ID 중 존재하지 않는 알러지 번호가 있습니다.");
        }

        List<ChildAllergy> allergiesHave = allergies.stream().map(
                allergy -> ChildAllergy.builder()
                .child(child)
                .allergy(allergy)
                .build()).toList();

        List<ChildAllergy> savedAllergies = childAllergyRepository.saveAll(allergiesHave);

        return savedAllergies.stream().map(ChildAllergy::getId).toList();
    }

    public ChildDietResponse getMain(Integer childId, Member member) {
        return ChildDietResponse.builder()
                .childDetailResponse(getChildDetail(childId, member))
                .dietList(dietRepository.getDietByDate(childId, LocalDate.now(), member)).build();
    }

    public ChildDetailResponse getChildDetail(Integer childId, Member member) {
        Growth growth = growthRepository
                .findTopByChildIdOrderByCreatedTimeDesc(childId)
                .orElseThrow(() -> new ChildNotFoundException("해당하는 자녀가 없습니다."));

        if(!member.equals(growth.getChild().getMember())) {
            throw new ChildAccessDeniedException("아이 조회 권한이 없습니다.");
        }

        return ChildDetailResponse.convertEntityToDto(growth);
    }

    public GrowthPageResponse getGrowthList(Integer childId, Pageable pageable, Member member) {
        Page<Growth> growthPage = growthRepository.findByChildIdOrderByIdDesc(childId, pageable);

        if(growthPage.isEmpty()) {
            throw new ChildNotFoundException("해당하는 자녀가 없습니다.");
        }

        if(!member.equals(growthPage.getContent().getFirst().getChild().getMember())) {
            throw new ChildAccessDeniedException("아이 조회 권한이 없습니다.");
        }

        Page<GrowthResponse> growthResponsePage = growthPage.map(GrowthResponse::ConvertEntityToDto);

        List<GrowthResponse> growthResponseList = growthResponsePage.getContent();
        List<Long> monthList = growthResponseList.stream().map(GrowthResponse::getMonth).toList();

        // 평균 성장 데이터 조회
        List<AverageGrowthResponse> averageGrowthList = averageGrowthRepository.findByGenderAndGrowMonthIn(growthResponseList.getFirst().getGender(), monthList)
                .stream()
                .map(AverageGrowthResponse::ConvertEntityToDto)
                .toList();

        return GrowthPageResponse.builder()
                .last(growthResponsePage.isLast())
                .growthList(growthResponseList)
                .averageList(averageGrowthList)
                .build();
    }

    @Transactional
    public ChildInfoResponse saveImage(Integer childId, MultipartFile file, Member member) {
        Child child = childRepository.findChildById(childId)
                .orElseThrow(() -> new ChildNotFoundException("해당하는 자녀가 없습니다."));

        if(!member.equals(child.getMember())) {
            throw new ChildAccessDeniedException("조회 권한이 없습니다.");
        }

        child.setImageUrl(s3Util.uploadImage(file));

        return ChildInfoResponse.ConvertEntityToDto(child);
    }
}
