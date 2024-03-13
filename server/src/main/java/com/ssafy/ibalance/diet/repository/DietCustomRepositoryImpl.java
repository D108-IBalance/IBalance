package com.ssafy.ibalance.diet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.*;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;

@RequiredArgsConstructor
public class DietCustomRepositoryImpl implements DietCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date) {
        Map<Diet, List<DietMenu>> transform = jpaQueryFactory.select(diet, dietMenu)
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.child.id.eq(childId).and(diet.dietDate.eq(date)))
                .transform(
                        groupBy(diet).as(
                                list(dietMenu)
                        )
                );

        return transform.entrySet().stream()
                .map(entry -> DietByDateResponse.builder()
                        .dietId(entry.getKey().getId())
                        .dietDate(entry.getKey().getDietDate())
                        .sequence(entry.getKey().getSequence())
                        .dietMenuList(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
