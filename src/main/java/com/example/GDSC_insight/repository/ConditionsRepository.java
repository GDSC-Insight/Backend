package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {
}
