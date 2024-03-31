package com.ssafy.ibalance.child.repository.childAllergy;

import java.util.List;

public interface ChildAllergyCustomRepository {
    List<String> getChildAllergyName(List<Long> childAllergyList);
}
