package com.ssafy.ibalance.member.repository;

import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.type.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByCodeAndProvider(String code, OAuthProvider oAuthProvider);
}
