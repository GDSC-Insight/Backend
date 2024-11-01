package com.example.GDSC_insight.service;

import com.example.GDSC_insight.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;

    public int countApplicationsByAnnouncementId(Long announcementId) {
        return applicationRepository.countByAnnouncementId(announcementId);
    }
}
