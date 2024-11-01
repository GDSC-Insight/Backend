package com.example.GDSC_insight.config.auth.controller;

import com.example.GDSC_insight.config.auth.domain.CorporatePrincipalDetails;
import com.example.GDSC_insight.config.jwt.JwtTokenProvider;
import com.example.GDSC_insight.domain.Corporate;
import com.example.GDSC_insight.dto.LoginRequest;
import com.example.GDSC_insight.dto.LoginResponse;
import com.example.GDSC_insight.service.CorporateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CorporateService corporateService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/auth/corporate/login")
    public ResponseEntity<?> corporateLogin(@RequestBody LoginRequest loginRequest) {

        LoginResponse isLogin = corporateService.login(loginRequest);

        if (isLogin.isSuccess()) {
            String jwt = jwtTokenProvider.generateToken(isLogin.getLoginId(), "ROLE_CORPORATE");

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/api/auth/user/login")
    public ResponseEntity<?> individualLogin(@RequestBody LoginRequest loginRequest) {

        LoginResponse isLogin = corporateService.login(loginRequest);

        if (isLogin.isSuccess()) {
            String jwt = jwtTokenProvider.generateToken(isLogin.getLoginId(), "ROLE_CORPORATE");

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    public Corporate getUser(Authentication authentication) {
        return getPrincipalUser(authentication);
    }

    private Corporate getPrincipalUser(Authentication authentication) {
        CorporatePrincipalDetails principal = (CorporatePrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}