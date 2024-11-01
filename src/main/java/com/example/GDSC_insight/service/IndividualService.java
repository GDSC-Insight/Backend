package com.example.GDSC_insight.service;

import com.example.GDSC_insight.domain.Individual;
import com.example.GDSC_insight.dto.IndividualRegisterRequest;
import com.example.GDSC_insight.dto.LoginRequest;
import com.example.GDSC_insight.dto.LoginResponse;
import com.example.GDSC_insight.repository.IndividualRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndividualService {

    private final IndividualRepository individualRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Individual> individual = individualRepository.findByLoginId(loginRequest.getUsername());

        if (individual.isPresent()) {
            Individual user = individual.get();
            boolean isPasswordValid = user.getPassword().equals(loginRequest.getPassword());
            return new LoginResponse(isPasswordValid, user.getLoginId());
        }

        return new LoginResponse(false, null);
    }

    public void register(IndividualRegisterRequest request) {
        Individual individual = Individual.builder().loginId(request.getLoginId()).password(request.getPassword())
                .name(request.getName()).address(request.getAddress()).phoneNumber(request.getPhoneNumber())
                .bankAccountNumber(request.getBankAccountNumber()).numChildren(3).singleParent(true).incomeLevel(3)
                .role("INDIVIDUAL")
                .build();
        individualRepository.save(individual);
    }
}
