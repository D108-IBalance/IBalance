package com.ssafy.ibalance.diet.repository.dietmaterial;

import com.ssafy.ibalance.diet.entity.DietMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietMaterialRepository extends JpaRepository<DietMaterial, Long>, DietMaterialCustomRepository {
}
