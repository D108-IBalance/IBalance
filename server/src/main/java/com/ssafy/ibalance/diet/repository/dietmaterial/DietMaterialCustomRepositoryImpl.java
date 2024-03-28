package com.ssafy.ibalance.diet.repository.dietmaterial;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.diet.dto.response.PickyMaterialResponse;
import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.list;
import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMaterial.dietMaterial;

@RequiredArgsConstructor
public class DietMaterialCustomRepositoryImpl implements DietMaterialCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PickyResultResponse getPickyResult(Member member, Integer childId, LocalDate startDate) {

        Map<Child, List<String>> pickyResult = jpaQueryFactory.select(child, dietMaterial.material)
                .from(dietMaterial)
                .join(diet).on(dietMaterial.diet.eq(diet))
                .join(child).on(diet.child.eq(child))
                .where(child.id.eq(childId)
                        .and(diet.createdTime.after(startDate.atStartOfDay()))
                        .and(dietMaterial.picky.eq(true)))
                .transform(
                        GroupBy.groupBy(child).as(list(dietMaterial.material))
                );

        Child child = pickyResult.keySet().stream().findFirst().orElseGet(null);

        if(child == null) {
            return getPickyResultResponse(startDate, new ArrayList<>());
        }

        if(!member.equals(child.getMember())) {
            throw new ChildAccessDeniedException("해당 아이의 정보에 접근할 수 있는 권한이 없습니다.");
        }

        List<PickyMaterialResponse> materialResponseList = pickyResult.get(child).stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> PickyMaterialResponse.builder()
                        .material(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .toList();

        return getPickyResultResponse(startDate, materialResponseList);
    }

    private PickyResultResponse getPickyResultResponse(LocalDate startDate, List<PickyMaterialResponse> pickyFrequencyList) {
        return PickyResultResponse.builder()
                .startDate(startDate)
                .pickyMaterials(pickyFrequencyList)
                .build();
    }
}
