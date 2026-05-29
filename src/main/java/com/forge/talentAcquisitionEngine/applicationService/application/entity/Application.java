package com.forge.talentAcquisitionEngine.applicationService.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.applicationService.application.enums.Stage;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.enums.Source;
import com.forge.talentAcquisitionEngine.interviewService.interview.entity.Interview;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
    name = "application",
    indexes = {
        @Index(
            name = "idx_application_candidate",
            columnList = "candidate_id"
        ),
        @Index(
            name = "idx_application_demand",
            columnList = "demand_id"
        ),
        @Index(
            name = "idx_application_stage",
            columnList = "current_stage"
        ),
        @Index(
            name = "idx_application_score",
            columnList = "ai_score"
        )
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_candidate_demand",
            columnNames = {
                "candidate_id",
                "demand_id"
            }
        )
    }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long ApplicationId;

    // =====================================================
    // CANDIDATE
    // =====================================================

    @NotNull(message = "Candidate is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "candidate_id",
        nullable = false
    )
    private ExternalCandidate candidate;

    // =====================================================
    // TEMPORARY DEMAND REFERENCE
    // REPLACE WITH Demand ENTITY LATER
    // =====================================================

    @NotNull(message = "Demand id is required")
    @Positive(message = "Demand id must be positive")
    @Column(name = "demand_id", nullable = false)
    private Long demandId;

    // =====================================================
    // SOURCE
    // =====================================================

    @NotNull(message = "Source is required")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "source",
        nullable = false,
        length = 50
    )
    private Source source;

    // =====================================================
    // RESUME
    // =====================================================

    @NotBlank(message = "Resume file path is required")
    @Size(max = 500)
    @Column(
        name = "resume_file_path",
        nullable = false,
        length = 500
    )
    private String resumeFilePath;

    @NotBlank(message = "Original filename is required")
    @Size(max = 255)
    @Column(
        name = "resume_original_filename",
        nullable = false,
        length = 255
    )
    private String resumeOriginalFilename;

    // =====================================================
    // SKILL ANALYSIS
    // =====================================================

    @ElementCollection
    @CollectionTable(
        name = "application_matched_skills",
        joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "skill")
    private List<String> matchedSkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "application_missing_skills",
        joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "skill")
    private List<String> missingSkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "application_other_skills",
        joinColumns = @JoinColumn(name = "application_id")
    )
    @Column(name = "skill")
    private List<String> otherSkills = new ArrayList<>();

    // =====================================================
    // AI
    // =====================================================

    @NotNull(message = "AI score is required")
    @Min(value = 0)
    @Max(value = 100)
    @Column(
        name = "ai_score",
        nullable = false
    )
    private Integer aiScore;

    @Size(max = 1000)
    @Column(
        name = "ai_rationale",
        columnDefinition = "TEXT"
    )
    private String aiRationale;

    // =====================================================
    // STAGE
    // =====================================================

    @NotNull(message = "Current stage is required")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "current_stage",
        nullable = false,
        length = 50
    )
    private Stage currentStage;

    @Size(max = 500)
    @Column(
        name = "stage_move_reason",
        columnDefinition = "TEXT"
    )
    private String stageMoveReason;

    // =====================================================
    // REAPPLY RULES
    // =====================================================

    @NotNull
    @Column(
        name = "blocked_from_reapply",
        nullable = false
    )
    private Boolean blockedFromReapply = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "reapply_allowed_after")
    private LocalDateTime reapplyAllowedAfter;

    // =====================================================
    // NOTES
    // =====================================================

    @Size(max = 2000)
    @Column(
        name = "free_notes",
        columnDefinition = "TEXT"
    )
    private String freeNotes;

    // =====================================================
    // REFERRAL
    // =====================================================

    @Size(max = 100)
    @Column(
        name = "referral_code",
        length = 100
    )
    private String referralCode;

    // =====================================================
    // REJECTION
    // =====================================================

    @Size(max = 1000)
    @Column(
        name = "rejection_reason",
        columnDefinition = "TEXT"
    )
    private String rejectionReason;

    // =====================================================
    // TIMELINE
    // =====================================================

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(
        name = "applied_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime appliedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "screening_at")
    private LocalDateTime screeningAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "technical_at")
    private LocalDateTime technicalAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "interview_at")
    private LocalDateTime interviewAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "final_round_at")
    private LocalDateTime finalRoundAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "offer_at")
    private LocalDateTime offerAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "hired_at")
    private LocalDateTime hiredAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    // =====================================================
    // INTERVIEWS
    // =====================================================

    @OneToMany(
        mappedBy = "application",
        fetch = FetchType.LAZY
    )
    private List<Interview> interviews = new ArrayList<>();

    // =====================================================
    // AUDIT
    // =====================================================

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(
        name = "created_at",
        updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // =====================================================
    // DEFAULTS
    // =====================================================

    @PrePersist
    public void prePersist() {

        if (currentStage == null) {
            currentStage = Stage.APPLIED;
        }

        if (blockedFromReapply == null) {
            blockedFromReapply = false;
        }
    }

    @PreUpdate
    public void preUpdate() {

        LocalDateTime now = LocalDateTime.now();

        switch (currentStage) {

            case SCREENING -> {
                if (screeningAt == null) {
                    screeningAt = now;
                }
            }

            case TECHNICAL -> {
                if (technicalAt == null) {
                    technicalAt = now;
                }
            }

            case INTERVIEW -> {
                if (interviewAt == null) {
                    interviewAt = now;
                }
            }

            case FINAL_ROUND -> {
                if (finalRoundAt == null) {
                    finalRoundAt = now;
                }
            }

            case OFFERED -> {
                if (offerAt == null) {
                    offerAt = now;
                }
            }

            case HIRED -> {
                if (hiredAt == null) {
                    hiredAt = now;
                }
            }

            case REJECTED -> {
                if (rejectedAt == null) {
                    rejectedAt = now;
                }
            }

            default -> {
            }
        }
    }
}