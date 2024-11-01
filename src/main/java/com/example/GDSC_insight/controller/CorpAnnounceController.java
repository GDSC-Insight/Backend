package com.example.GDSC_insight.controller;

import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.domain.Conditions;
import com.example.GDSC_insight.dto.PostAnnounce;
import com.example.GDSC_insight.service.AnnouncementService;
import com.example.GDSC_insight.service.ConditionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/corporate/announcement")
public class CorpAnnounceController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ConditionsService conditionsService;

    @PostMapping("")
    public ResponseEntity<String> writePost(@ModelAttribute PostAnnounce postAnnounce) {

        Announcement announcement = new Announcement();
        Conditions conditions = new Conditions();
        announcement.setTitle(postAnnounce.getTitle());
        announcement.setDescription(postAnnounce.getDescription());

        // 이미지 BLOB 처리
        if (postAnnounce.getImageFile() != null && !postAnnounce.getImageFile().isEmpty()) {
            try {
                byte[] imageBytes = postAnnounce.getImageFile().getBytes();
                announcement.setImage(imageBytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
            }
        }


        // 나머지 필드 설정
        announcement.setDeadline(postAnnounce.getDeadline());
        announcement.setNumTarget(postAnnounce.getNum_target());
        Announcement savedAnnouncement = announcementService.save(announcement);

        conditions.setAnnouncement(savedAnnouncement);
        conditions.setNumChildren(postAnnounce.getNum_children());
        conditions.setSingleParent(postAnnounce.getSingle_parent());
        conditions.setIncomeLevel(postAnnounce.getIncome_level());

        conditionsService.save(conditions);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{announcement_id}")
    public ResponseEntity<String> updatePost(@PathVariable Long announcement_id,
                                             @ModelAttribute PostAnnounce postAnnounce) {
        // Announcement 객체를 ID로 조회
        Announcement existingAnnouncement = announcementService.findById(announcement_id);
        if (existingAnnouncement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found");
        }

        // Announcement 객체 업데이트
        existingAnnouncement.setTitle(postAnnounce.getTitle());
        existingAnnouncement.setDescription(postAnnounce.getDescription());

        // 이미지 BLOB 처리
        if (postAnnounce.getImageFile() != null && !postAnnounce.getImageFile().isEmpty()) {
            try {
                byte[] imageBytes = postAnnounce.getImageFile().getBytes();
                existingAnnouncement.setImage(imageBytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
            }
        }

        // 나머지 필드 업데이트
        existingAnnouncement.setDeadline(postAnnounce.getDeadline());
        existingAnnouncement.setNumTarget(postAnnounce.getNum_target());

        // Announcement 업데이트
        announcementService.save(existingAnnouncement);

        // Conditions 객체 업데이트
        Conditions conditions = conditionsService.findByAnnouncement(existingAnnouncement);
        if (conditions == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conditions not found");
        }
        conditions.setNumChildren(postAnnounce.getNum_children());
        conditions.setSingleParent(postAnnounce.getSingle_parent());
        conditions.setIncomeLevel(postAnnounce.getIncome_level());

        // Conditions 업데이트
        conditionsService.save(conditions);

        return ResponseEntity.ok().build();
    }
}
