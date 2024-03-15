package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Growth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrowthRepository extends JpaRepository<Growth, Long> {
    Optional<Growth> findTopByChildIdOrderByCreatedTimeDesc(Integer childId);
    Page<Growth> findByChildIdOrderByIdDesc(Integer childId, Pageable pageable);
}

