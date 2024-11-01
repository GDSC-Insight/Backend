package com.example.GDSC_insight.service;

import com.example.GDSC_insight.domain.Corporate;
import com.example.GDSC_insight.repository.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CorporateService {
    @Autowired
    private CorporateRepository corporateRepository;
    public Optional<Corporate> findById(Long id) {
        return corporateRepository.findById(id);
    }
}
