package com.example.GDSC_insight.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class corpAnnounceList {
    private int announcement_id;
    private String title;
    private LocalDateTime post_date;
    private LocalDateTime deadline;
    private int num_target;
    private int current_num_target;
}
