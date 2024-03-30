package com.ssafy.ibalance.diet.repository;

import com.ssafy.ibalance.diet.entity.DietMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietMenuRepository extends JpaRepository<DietMenu, Long> {
}
