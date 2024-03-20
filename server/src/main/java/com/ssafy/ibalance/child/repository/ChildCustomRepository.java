package com.ssafy.ibalance.child.repository;

import java.time.LocalDate;
import java.util.List;

public interface ChildCustomRepository {
    List<Integer> getMenuIdByCHildIdAndDate(Integer childId, LocalDate today);
}
