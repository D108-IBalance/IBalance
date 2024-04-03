package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllergyRepository extends JpaRepository<Allergy, Integer> {

    List<Allergy> findAllergyNameByIdIn(List<Integer> id);
}
