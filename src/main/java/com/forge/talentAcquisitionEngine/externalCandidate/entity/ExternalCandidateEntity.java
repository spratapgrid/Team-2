package com.forge.talentAcquisitionEngine.externalCandidate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.application.entity.ApplicationEntity;
import com.forge.talentAcquisitionEngine.externalCandidate.enums.Source;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "external_candidate")
public class ExternalCandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "candidate_id")
  private UUID id;

  @NotBlank(message = "First name required")
  private String firstName;

  @NotBlank(message = "Last name required")
  private String lastName;

  @NotBlank(message = "Email required")
  @Email(message = "Invalid email format")
  private String email;

  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
  @NotBlank(message = "Phone number is required")
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Source source;

  @URL(message = "Invalid resume URL")
  @NotBlank(message = "Resume URL is required")
  private String resumeUrl;

  @ElementCollection
  @CollectionTable(
          name = "candidate_skills",
          joinColumns = @JoinColumn(name = "candidate_id")
  )
  @Column(name = "skill")
  private List<String> skills;

  @Column(name = "gdpr_consent_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime gdprConsentAt;

  @Column(name = "deleted_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deletedAt;

  @OneToMany(mappedBy = "externalCandidate")
  private List<ApplicationEntity> applications;
}