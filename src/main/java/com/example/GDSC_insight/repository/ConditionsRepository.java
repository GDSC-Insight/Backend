package com.example.GDSC_insight.repository;

import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.domain.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {
    Conditions findByAnnouncement(Announcement announcement);

    @Modifying
    @Transactional
    @Query("DELETE FROM Conditions c WHERE c.announcement.id = :announcementId")
    void deleteByAnnouncementId(Long announcementId);
}
