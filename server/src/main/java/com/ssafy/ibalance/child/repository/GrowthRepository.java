package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Growth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowthRepository extends JpaRepository<Growth, Long> {
}
