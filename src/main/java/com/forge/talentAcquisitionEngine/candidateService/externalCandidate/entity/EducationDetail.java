package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "education_detail")
public class EducationDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "education_id")
  private Long educationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private ExternalCandidate candidate;

  @NotBlank(message = "Degree is required")
  @Size(max = 100)
  private String degree;

  @NotBlank(message = "Specialization is required")
  @Size(max = 100)
  private String specialization;

  @NotBlank(message = "Institution name is required")
  @Size(max = 150)
  private String institutionName;

  @JsonFormat(pattern = "yyyy")
  private Integer startYear;

  @JsonFormat(pattern = "yyyy")
  private Integer endYear;

  @DecimalMin(value = "0.0", message = "Percentage cannot be negative")
  @DecimalMax(value = "100.0", message = "Percentage must not exceed 100")
  private Float percentage;

}