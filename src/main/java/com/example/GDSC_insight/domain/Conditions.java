package com.example.GDSC_insight.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "conditions")
public class Conditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_children")
    private Integer numChildren;

    @Column(name = "single_parent")
    private Boolean singleParent;

    @Column(name = "income_level")
    private Integer incomeLevel;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false, foreignKey = @ForeignKey(name = "fk_conditions_announcement"))
    private Announcement announcement; // Announcement 엔티티와의 관계 설정
}
