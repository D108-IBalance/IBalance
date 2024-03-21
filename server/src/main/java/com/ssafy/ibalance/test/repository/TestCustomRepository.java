package com.ssafy.ibalance.test.repository;

import com.ssafy.ibalance.test.entity.TesterEntity;

import java.util.List;

public interface TestCustomRepository {

    List<TesterEntity> testFind(String name);
}
