package com.example.GDSC_insight.service;

import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement findById(Long announcementId) {
        // findById 메소드는 Optional을 반환하므로, 이를 처리하여 실제 Announcement를 반환
        Optional<Announcement> announcementOptional = announcementRepository.findById(announcementId);
        return announcementOptional.orElse(null); // 또는 적절한 예외 처리를 추가
    }
    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
}
