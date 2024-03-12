package com.ssafy.ibalance.child.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.dto.ChildDetailDto;
import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.child.entity.QGrowth.growth;

@Repository
@RequiredArgsConstructor
public class ChildCustomRepositoryImpl implements ChildCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ChildDetailResponse> getChildDetail(Integer childId) {
        ChildDetailDto childDetailDto = jpaQueryFactory.select(
                Projections.bean(
                        ChildDetailDto.class,
                        child.as("child"),
                        growth.as("growth")
                        )
                )
                .from(child)
                .join(growth).on(child.id.eq(growth.child.id))
                .where(child.id.eq(childId))
                .orderBy(growth.createdTime.desc())
                .limit(1)
                .fetchOne();

        if(childDetailDto == null) {
            return Optional.empty();
        }

        return Optional.of(ChildDetailResponse.builder()
                .childId(childId)
                .imageUrl(childDetailDto.getChild().getImageUrl())
                .name(childDetailDto.getChild().getName())
                .birthDate(childDetailDto.getChild().getBirthDate())
                .gender(childDetailDto.getChild().getGender())
                .height(childDetailDto.getGrowth().getHeight())
                .weight(childDetailDto.getGrowth().getWeight())
                .lastUpdateDate(childDetailDto.getGrowth().getCreatedTime().toLocalDate())
                .build());
    }
}
