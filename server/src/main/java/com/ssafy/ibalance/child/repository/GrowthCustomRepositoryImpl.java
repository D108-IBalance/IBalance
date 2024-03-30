package com.ssafy.ibalance.child.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.dto.HeightGrowthDto;
import com.ssafy.ibalance.child.dto.WeightGrowthDto;
import com.ssafy.ibalance.child.entity.Growth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.child.entity.QGrowth.growth;

@RequiredArgsConstructor
public class GrowthCustomRepositoryImpl implements GrowthCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<HeightGrowthDto> getHeightList(Integer childId, Pageable pageable) {
        List<HeightGrowthDto> content = jpaQueryFactory.select(
                        Projections.fields(HeightGrowthDto.class,
                                growth.createdTime.week().as("weekCreatedTime"),
                                growth.createdTime.max().as("createdTime"),
                                growth.height,
                                child
                        )
                )
                .from(growth)
                .join(child).on(growth.child.id.eq(child.id))
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth.height)
                .orderBy(growth.createdTime.max().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Growth> count = jpaQueryFactory.select(growth)
                .from(growth)
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth)
                .orderBy(growth.id.desc());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    @Override
    public Page<WeightGrowthDto> getWeightList(Integer childId, Pageable pageable) {
        List<WeightGrowthDto> content = jpaQueryFactory.select(
                        Projections.fields(WeightGrowthDto.class,
                                growth.createdTime.week().as("weekCreatedTime"),
                                growth.createdTime.max().as("createdTime"),
                                growth.weight,
                                child
                        )
                )
                .from(growth)
                .join(child).on(growth.child.id.eq(child.id))
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth.weight)
                .orderBy(growth.createdTime.max().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Growth> count = jpaQueryFactory.select(growth)
                .from(growth)
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth)
                .orderBy(growth.id.desc());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }
}
