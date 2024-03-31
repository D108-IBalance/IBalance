package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.child.repository.childAllergy.ChildAllergyRepository;
import com.ssafy.ibalance.common.util.DtoConverter;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.common.util.UrlBuilder;
import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;
import com.ssafy.ibalance.diet.dto.response.picky.PickyRecipe;
import com.ssafy.ibalance.diet.repository.dietmaterial.DietMaterialRepository;
import com.ssafy.ibalance.diet.type.PeriodUnit;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PickyService {

    private final DietMaterialRepository dietMaterialRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;
    private final ChildAllergyRepository childAllergyRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;
    private final UrlBuilder urlBuilder;
    private final DtoConverter dtoConverter;


    public PickyResultResponse getPickyResult(Member member, Integer childId, PeriodUnit periodUnit) {
        return dietMaterialRepository.getPickyResult(member, childId, PeriodUnit.getStartDate(periodUnit));
    }

    public List<PickyRecipe> getSolutionRecipeList(Member member, Integer childId, String material, Integer offset, String lastId) {

        RedisChildAllergy redisAllergyInfo = redisChildAllergyRepository.findById(childId).orElseThrow(
                () -> new ChildNotFoundException("입력된 아이디로 등록된 아이를 찾을 수 없습니다.")
        );

        if(!redisAllergyInfo.getMemberId().equals(member.getId())) {
            throw new ChildAccessDeniedException("해당 아이의 정보에 접근할 권한이 없습니다.");
        }

        List<String> childAllergyName = childAllergyRepository.getChildAllergyName(redisAllergyInfo.getChildAllergyId());

        if(lastId == null || lastId.isEmpty() || lastId.isBlank()) {
            lastId = "";
        }

        return getSolutionRecipeListFromFastAPI(childAllergyName, material, offset, lastId);
    }

    private List<PickyRecipe> getSolutionRecipeListFromFastAPI(List<String> allergyList, String material, Integer offset, String lastId) {
        String targetUrl = urlBuilder.getUrl("/picky",
                Map.of("matrl", material, "offset", offset, "lastid", lastId), false);

        ArrayList<LinkedHashMap<String, Object>> pickyResult =
                fastAPIConnectionUtil.postApiConnectionResult(targetUrl, allergyList, new ArrayList<>());

        return pickyResult.stream().
                map(result -> dtoConverter.convertFromMap(result, new PickyRecipe()))
                .toList();
    }
}
