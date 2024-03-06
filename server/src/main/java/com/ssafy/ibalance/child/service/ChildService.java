package com.ssafy.ibalance.child.service;

import com.ssafy.ibalance.child.dto.response.ChildListResponseDto;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public List<ChildListResponseDto> getChildList(Integer memberId) {
        List<Child> children = childRepository.findAllByMemberId(memberId);
        return children.stream().map(ChildListResponseDto::of).toList();
    }
}
