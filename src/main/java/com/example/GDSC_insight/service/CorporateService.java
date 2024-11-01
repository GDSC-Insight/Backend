package com.example.GDSC_insight.service;
import com.example.GDSC_insight.domain.Corporate;
import com.example.GDSC_insight.dto.CorporateRegisterRequest;
import com.example.GDSC_insight.dto.LoginResponse;
import com.example.GDSC_insight.dto.LoginRequest;
import com.example.GDSC_insight.repository.CorporateRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CorporateService {

    private final CorporateRepository corporateRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Corporate> corporate = corporateRepository.findByLoginId(loginRequest.getUsername());

        if (corporate.isPresent()) {
            Corporate user = corporate.get();
            boolean isPasswordValid = user.getPassword().equals(loginRequest.getPassword());
            return new LoginResponse(isPasswordValid, user.getLoginId());
        }

        return new LoginResponse(false, null);
    }

    public void register(CorporateRegisterRequest request) {
        Corporate corporate = Corporate.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .role("CORPORATE")
                .build();
        corporateRepository.save(corporate);
    }

    public Optional<Corporate> findById(Long id) {
        return corporateRepository.findById(id);

}
