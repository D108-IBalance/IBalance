package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer>, ChildCustomRepository {

    List<Child> findAllByMemberId(Integer memberId);

    Optional<Child> findChildById(Integer childId);
}
