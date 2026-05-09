package com.forge.talentAcquisitionEngine.interview.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.application.entity.ApplicationEntity;
import com.forge.talentAcquisitionEngine.interview.enums.Status;
import com.forge.talentAcquisitionEngine.interview.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "interview")
public class InterviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "interview_id")
    private UUID id;

    @JoinColumn(name = "application_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationEntity application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Integer durationMins;

    @ElementCollection
    @CollectionTable(
            name = "interview_interviewers",
            joinColumns = @JoinColumn(name = "interview_id")
    )
   @JoinColumn(name = "employee_id")
   private List<UUID> interviewers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "calendar_event_id")
    private String calendarEventId;
}