package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // 추가적인 쿼리 메소드를 정의할 수 있습니다.
}
