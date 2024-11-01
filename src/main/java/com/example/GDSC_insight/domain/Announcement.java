package com.example.GDSC_insight.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false, foreignKey = @ForeignKey(name = "fk_announcement_corporate"))
    private Corporate author; // Corporate 엔티티가 있어야 합니다.

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "post_date", nullable = false, updatable = false)
    private LocalDateTime postDate = LocalDateTime.now();

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "num_target")
    private Integer numTarget;

    // Corporate 엔티티는 다른 파일에 정의해야 하며, 기본 키로 id가 설정되어 있어야 합니다.
}
