package com.ssafy.ibalance.member.repository;

import com.ssafy.ibalance.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByCode(String code);
    Optional<Member> findByCode(String code);
}
