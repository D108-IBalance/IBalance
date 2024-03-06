package com.ssafy.ibalance.test.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.test.entity.TesterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.ibalance.test.entity.QTesterEntity.testerEntity;

@Repository
@RequiredArgsConstructor
public class TestCustomRepositoryImpl implements TestCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TesterEntity> testFind(String name) {
        return jpaQueryFactory.selectFrom(testerEntity)
                .where(testerEntity.name.eq(name))
                .fetch();
    }
}
