package com.example.GDSC_insight.controller;

import com.example.GDSC_insight.config.auth.domain.CorporatePrincipalDetails;
import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.domain.Conditions;
import com.example.GDSC_insight.domain.Corporate;
import com.example.GDSC_insight.dto.PostAnnounce;
import com.example.GDSC_insight.dto.corpAnnounce;
import com.example.GDSC_insight.dto.corpAnnounceList;
import com.example.GDSC_insight.service.AnnouncementService;
import com.example.GDSC_insight.service.ApplicationService;
import com.example.GDSC_insight.service.ConditionsService;
import com.example.GDSC_insight.service.CorporateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/corporate/announcement")
public class CorpAnnounceController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ConditionsService conditionsService;
    @Autowired
    private CorporateService corporateService;
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("")
    public ResponseEntity<String> writePost(@ModelAttribute PostAnnounce postAnnounce, Authentication authentication) {
        Corporate corporate=getUser(authentication);
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
        announcement.setAuthor(corporate);
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

    @DeleteMapping("{announcement_id}")
    public ResponseEntity<String> deletePost(@PathVariable Long announcement_id){
        // Announcement 존재 여부 확인
        Announcement existingAnnouncement = announcementService.findById(announcement_id);
        if (existingAnnouncement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 공고를 찾을 수 없습니다.");
        }
        // Announcement 삭제
        announcementService.deleteById(announcement_id);
        conditionsService.deleteByAnnouncementId(announcement_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<corpAnnounce> getPost(Authentication authentication){
        //기업 id 가져오기
        Corporate corporate=getUser(authentication);
        //기업 id로 Corporate 정보 검색

        //데이터 조합
        List<corpAnnounceList> announcementList = new ArrayList<>();

        // Announcement 엔티티에서 데이터를 가져와서 announcementList에 추가
        List<Announcement> announcements = announcementService.findByAuthor(corporate.getId());
        for (Announcement announcement : announcements) {
            corpAnnounceList listItem = new corpAnnounceList();
            listItem.setAnnouncement_id(announcement.getId());
            listItem.setTitle(announcement.getTitle());
            listItem.setPost_date(announcement.getPostDate()); // LocalDateTime 형식에 맞게 변환
            listItem.setDeadline(announcement.getDeadline());
            listItem.setNum_target(announcement.getNumTarget());
            listItem.setCurrent_num_target(applicationService.countApplicationsByAnnouncementId(announcement.getId()));
            announcementList.add(listItem);
        }

        // corpAnnounce 객체 생성 및 값 설정
        corpAnnounce response = new corpAnnounce();
        response.setCorporate_id(corporate.getId());
        response.setName(corporate.getName());
        response.setAnnouncement(announcementList);

        return ResponseEntity.ok(response);
    }
    public Corporate getUser(Authentication authentication) {
        return getPrincipalUser(authentication);
    }
    private Corporate getPrincipalUser(Authentication authentication) {
        CorporatePrincipalDetails principal = (CorporatePrincipalDetails) authentication.getPrincipal();
        return principal.getUser();
    }
}
