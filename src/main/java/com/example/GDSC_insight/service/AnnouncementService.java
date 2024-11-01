package com.example.GDSC_insight.service;

import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
}
