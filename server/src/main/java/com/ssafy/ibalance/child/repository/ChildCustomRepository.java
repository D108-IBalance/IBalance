package com.ssafy.ibalance.child.repository;

import java.time.LocalDate;
import java.util.List;

public interface ChildCustomRepository {

    List<String> getMenuIdByChildIdAndDate(Integer childId, LocalDate today);
}
