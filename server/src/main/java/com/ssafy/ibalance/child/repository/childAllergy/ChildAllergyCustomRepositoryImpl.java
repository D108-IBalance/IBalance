package com.ssafy.ibalance.child.repository.childAllergy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.ibalance.child.entity.QAllergy.allergy;
import static com.ssafy.ibalance.child.entity.QChildAllergy.childAllergy;

@RequiredArgsConstructor
public class ChildAllergyCustomRepositoryImpl implements ChildAllergyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getChildAllergyName(List<Long> childAllergyList) {
        if(childAllergyList == null) {
            childAllergyList = List.of();
        }

        return jpaQueryFactory.select(allergy.allergyName)
                .from(childAllergy)
                .join(allergy).on(childAllergy.allergy.eq(allergy))
                .where(childAllergy.id.in(childAllergyList))
                .fetch();
    }
}
