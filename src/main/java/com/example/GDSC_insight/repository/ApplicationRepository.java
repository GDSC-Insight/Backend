package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    int countByAnnouncementId(Long announcementId);
}
