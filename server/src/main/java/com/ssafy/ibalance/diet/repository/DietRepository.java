package com.ssafy.ibalance.diet.repository;

import com.ssafy.ibalance.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long>, DietCustomRepository {
    
}
