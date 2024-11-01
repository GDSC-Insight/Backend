package com.example.GDSC_insight.controller;

import com.example.GDSC_insight.dto.CorporateRegisterRequest;
import com.example.GDSC_insight.dto.IndividualRegisterRequest;
import com.example.GDSC_insight.service.CorporateService;
import com.example.GDSC_insight.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
@Log4j2
public class RegisterController {

    private final CorporateService corporateService;
    private final IndividualService individualService;


    @PostMapping("/corporate")
    public ResponseEntity<Void> registerCorporate(@RequestBody CorporateRegisterRequest request) {
        try {
            corporateService.register(request);
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Void> registerUser(@RequestBody IndividualRegisterRequest request) {
        try {
            individualService.register(request);
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }
}
