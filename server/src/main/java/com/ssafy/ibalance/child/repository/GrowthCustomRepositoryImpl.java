package com.ssafy.ibalance.child.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.dto.HeightGrowthDto;
import com.ssafy.ibalance.child.dto.WeightGrowthDto;
import com.ssafy.ibalance.notification.dto.NotifyTargetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.child.entity.QGrowth.growth;
import static com.ssafy.ibalance.member.entity.QMember.member;

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

        JPAQuery<HeightGrowthDto> count = jpaQueryFactory.select(
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
                .orderBy(growth.createdTime.max().desc());

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

        JPAQuery<WeightGrowthDto> count = jpaQueryFactory.select(
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
                .orderBy(growth.createdTime.max().desc());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    @Override
    public List<NotifyTargetDto> getNotifyTargetList() {
        return jpaQueryFactory.select(
                Projections.fields(NotifyTargetDto.class,
                        member.id,
                        growth.createdTime.max().as("lastUpdate")))
                .from(child)
                .join(growth).on(child.id.eq(growth.child.id))
                .join(member).on(child.member.id.eq(member.id))
                .groupBy(child.id)
                .having(growth.createdTime.max().lt(LocalDateTime.now().minusDays(6)))
                .fetch();
    }
}
