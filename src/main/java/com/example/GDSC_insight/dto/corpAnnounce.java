package com.example.GDSC_insight.dto;

import lombok.Data;

import java.util.List;

@Data
public class corpAnnounce {
    private long corporate_id;
    private String name;
    private List<corpAnnounceList> announcement;
}
