package com.example.GDSC_insight.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostAnnounce {
    private String title;
    private String description;
    private MultipartFile imageFile;
    private LocalDateTime deadline;
    private int num_target;
    private int num_children;
    private Boolean single_parent;
    private int income_level;
}
