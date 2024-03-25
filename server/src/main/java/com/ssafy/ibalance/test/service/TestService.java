package com.ssafy.ibalance.test.service;

import com.ssafy.ibalance.test.dto.request.TestSaveRequest;
import com.ssafy.ibalance.test.entity.TesterEntity;
import com.ssafy.ibalance.test.repository.TesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TesterRepository testerRepository;

    public void saveTestEntity(TestSaveRequest request) {
        TesterEntity entity = TesterEntity.builder()
                .name(request.name())
                .address(request.address())
                .build();

        TesterEntity save = testerRepository.save(entity);
        System.out.println(save);
    }

    public List<TesterEntity> getTestUsingAddress(String address) {
        return testerRepository.findAllByAddressContaining(address);
    }

    public List<TesterEntity> getTestUsingName(String name) {
        return testerRepository.testFind(name);
    }
}
