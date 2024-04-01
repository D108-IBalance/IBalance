package com.ssafy.ibalance.test.repository;

import com.ssafy.ibalance.test.entity.TesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TesterRepository extends JpaRepository<TesterEntity, Long>, TestCustomRepository {

    List<TesterEntity> findAllByAddressContaining(String address);
}
