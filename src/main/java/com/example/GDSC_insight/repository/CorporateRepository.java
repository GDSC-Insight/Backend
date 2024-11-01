package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, Long> {
}
