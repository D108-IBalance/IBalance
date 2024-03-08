package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.ChildAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildAllergyRepository extends JpaRepository<ChildAllergy, Long> {

}
