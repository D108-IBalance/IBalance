package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.ChildAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildAllergyRepository extends JpaRepository<ChildAllergy, Long> {
    List<ChildAllergy> findByChild_id(Integer id);

    List<ChildAllergy> findByIdIn(List<Long> id);
}