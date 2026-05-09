package com.forge.talentAcquisitionEngine.scorecard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.interview.entity.InterviewEntity;
import com.forge.talentAcquisitionEngine.scorecard.enums.Recommendation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "scorecard",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "interview_interviewer",
                        columnNames = {"interview_id", "interviewer_id"}
                )
        }
)
public class ScorecardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "scorecard_id")
    private UUID id;

    @JoinColumn(name = "interview_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InterviewEntity interview;

    @JoinColumn(name = "employee_id")
    private UUID employeeId;

    @ElementCollection
    @CollectionTable(
            name = "scorecard_ratings",
            joinColumns = @JoinColumn(name = "scorecard_id")
    )
    @MapKeyColumn(name = "Topics")
    @Column(name = "ratings")
    private Map<String, Integer> ratings;

    @Column(name = "strengths", columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String concerns;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recommendation recommendation;

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submittedAt;
}