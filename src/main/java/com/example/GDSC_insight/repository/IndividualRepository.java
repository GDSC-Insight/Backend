package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Individual;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Integer> {

    Optional<Individual> findByLoginId(String loginId);
}