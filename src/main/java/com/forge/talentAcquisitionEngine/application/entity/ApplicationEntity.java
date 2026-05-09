package com.forge.talentAcquisitionEngine.application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.application.enums.Stage;
import com.forge.talentAcquisitionEngine.externalCandidate.entity.ExternalCandidateEntity;
import com.forge.talentAcquisitionEngine.externalCandidate.enums.Source;
import com.forge.talentAcquisitionEngine.interview.entity.InterviewEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "application")
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    @Column(name = "application_id")
    private UUID id;

    @JoinColumn(name = "candidate_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ExternalCandidateEntity externalCandidate;

    @JoinColumn(name = "demand_id")
    private UUID demandId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stage stage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Source source;

    @CreationTimestamp
    @Column(name = "applied_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appliedAt;

    @Column(name = "rejected_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rejectedAt;

    @Size(max = 100, message = "word limit is 100")
    private String rejectionReason;

    private String referralCode;

    @PreUpdate
    private void onUpdate() {
        if (Stage.REJECTED.equals(this.stage) && this.rejectedAt == null) {
            this.rejectedAt = LocalDateTime.now();
        }
    }

    @OneToMany(mappedBy = "application")
    private List<InterviewEntity> interviews;

}