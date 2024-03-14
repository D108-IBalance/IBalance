package com.ssafy.ibalance.growth.repository;

import com.ssafy.ibalance.growth.entity.Growth;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrowthRepository extends JpaRepository<Growth, Long> {
    Optional<Growth> findTopByChildIdOrderByCreatedTimeDesc(Integer childId);
    List<Growth> findTop5ByChildIdOrderByIdDesc(Integer childId);
    List<Growth> findByChildIdOrderByIdDesc(Integer childId, Pageable pageable);
}

