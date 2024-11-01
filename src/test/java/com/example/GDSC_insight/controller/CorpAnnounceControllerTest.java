package com.example.GDSC_insight.controller;

import com.example.GDSC_insight.domain.Announcement;
import com.example.GDSC_insight.domain.Conditions;
import com.example.GDSC_insight.dto.PostAnnounce;
import com.example.GDSC_insight.service.AnnouncementService;
import com.example.GDSC_insight.service.ConditionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CorpAnnounceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnnouncementService announcementService;

    @Mock
    private ConditionsService conditionsService;

    @InjectMocks
    private CorpAnnounceController corpAnnounceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(corpAnnounceController).build();
    }

    @Test
    void testWriteComment() throws Exception {
        // 준비 단계
        PostAnnounce postAnnounce = new PostAnnounce();
        postAnnounce.setTitle("Test Title");
        postAnnounce.setDescription("Test Description");

        // 이미지 파일 설정
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test-image.jpg", "image/jpeg", "test image content".getBytes());
        postAnnounce.setImageFile(imageFile);

        // LocalDateTime을 설정
        LocalDateTime deadline = LocalDateTime.now(); // 현재 시간을 사용
        postAnnounce.setDeadline(deadline); // Deadlines는 LocalDateTime으로 사용됨

        // 나머지 필드 설정
        postAnnounce.setNum_target(100);
        postAnnounce.setNum_children(2);
        postAnnounce.setSingle_parent(true);
        postAnnounce.setIncome_level(1);

        Announcement savedAnnouncement = new Announcement();
        savedAnnouncement.setId(1L); // 예시로 ID 설정

        // announcementService의 save 메서드가 Announcement 객체를 저장할 때, savedAnnouncement을 반환하도록 설정
        when(announcementService.save(any(Announcement.class))).thenReturn(savedAnnouncement);

        // conditionsService의 save 메서드가 조건 객체를 반환하는 경우
        Conditions condition = new Conditions(); // 여기에 실제 Conditions 객체를 생성하고 설정하세요.
        when(conditionsService.save(any(Conditions.class))).thenReturn(condition); // 반환 값 설정

        // 테스트 실행
        mockMvc.perform(multipart("/api/corporate/announcement")
                        .file(imageFile)
                        .param("title", postAnnounce.getTitle())
                        .param("description", postAnnounce.getDescription())
                        .param("deadline", deadline.toString()) // LocalDateTime을 문자열로 변환하여 사용
                        .param("num_target", String.valueOf(postAnnounce.getNum_target()))
                        .param("num_children", String.valueOf(postAnnounce.getNum_children()))
                        .param("single_parent", String.valueOf(postAnnounce.getSingle_parent()))
                        .param("income_level", String.valueOf(postAnnounce.getIncome_level())))
                .andExpect(status().isOk());

        // 검증
        verify(announcementService, times(1)).save(any(Announcement.class));
        verify(conditionsService, times(1)).save(any(Conditions.class));
    }

}
