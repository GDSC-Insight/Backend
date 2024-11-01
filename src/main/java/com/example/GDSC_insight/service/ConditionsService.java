package com.example.GDSC_insight.service;

import com.example.GDSC_insight.domain.Conditions;
import com.example.GDSC_insight.repository.ConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionsService {
    @Autowired
    private ConditionsRepository conditionsRepository;

    public Conditions save(Conditions conditions){
        return conditionsRepository.save(conditions);
    }
}
