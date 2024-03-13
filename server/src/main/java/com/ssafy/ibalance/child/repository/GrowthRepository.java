package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Growth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrowthRepository extends JpaRepository<Growth, Long> {
    Optional<Growth> findTopByChildIdOrderByCreatedTimeDesc(Integer childId);
    List<Growth> findTop3ByChildIdOrderByIdDesc(Integer childId);
}

