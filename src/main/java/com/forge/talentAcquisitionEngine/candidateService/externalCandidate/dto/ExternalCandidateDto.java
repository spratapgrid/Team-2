package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.enums.Source;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ExternalCandidateDto {

  private Long candidateId;

  @NotBlank(message = "First name is required")
  @Size(min = 1, message = "First name must be minimum 1 characters")
  @Pattern(
      regexp = "^[A-Za-z ]+$",
      message = "First name must contain only letters and spaces"
  )
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Size(min = 1, message = "Last name must be minimum 1 characters")
  @Pattern(
      regexp = "^[A-Za-z ]+$",
      message = "Last name must contain only letters and spaces"
  )
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = 150, message = "Email must not exceed 150 characters")
  private String email;

  @NotBlank(message = "Phone number is required")
  @Pattern(
      regexp = "^[6-9][0-9]{9}$",
      message = "Phone number must be a valid 10 digit Indian mobile number"
  )
  private String phoneNumber;

  @NotNull(message = "Date of birth is required")
  @Past(message = "Date of birth must be in the past")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;

  @NotBlank(message = "Gender is required")
  @Size(max = 20, message = "Gender must not exceed 20 characters")
  private String gender;

  @NotBlank(message = "Address is required")
  @Size(max = 500, message = "Address must not exceed 500 characters")
  private String address;

  @DecimalMin(
      value = "0.0",
      message = "Total experience cannot be negative"
  )
  private Float totalExperienceYears;

  @DecimalMin(
      value = "0.0",
      message = "Total gap years cannot be negative"
  )
  private Float totalGapYears;

  @NotEmpty(message = "At least one skill is required")
  private List<
      @NotBlank(message = "Skill cannot be blank")
          String
      > skills;

  @Size(
      max = 100,
      message = "Company name must not exceed 100 characters"
  )
  private String companyName;

  @Size(
      max = 100,
      message = "Designation must not exceed 100 characters"
  )
  private String designation;

  @DecimalMin(
      value = "0.0",
      message = "Current CTC cannot be negative"
  )
  private Long currentCtc;

  @DecimalMin(
      value = "0.0",
      message = "Expected CTC cannot be negative"
  )
  private Long expectedCtc;

  @Min(
      value = 0,
      message = "Notice period cannot be negative"
  )
  private Integer noticePeriodDays;

  private Boolean willingToRelocate;

  @Size(
      max = 1000,
      message = "Free notes must not exceed 1000 characters"
  )
  private String freeNotes;

  @NotNull(message = "Source is required")
  private Source source;

  @Valid
  private List<EducationDetailDto> educationDetails;

  @Valid
  private List<CertificationDetailDto> certificationDetails;

  private String emailHash;

  private String phoneHash;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  private Boolean isDeleted;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deletedAt;

  @Size(
      max = 100,
      message = "Deleted by must not exceed 100 characters"
  )
  private String deletedBy;

  @Size(
      max = 500,
      message = "Delete reason must not exceed 500 characters"
  )
  private String deleteReason;

  private Boolean piiAnonymized;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime piiAnonymizedAt;

  private Boolean gdprDeleteRequested;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime gdprDeleteRequestedAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime gdprDeleteDueAt;

  private Boolean blockedFromReapply;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;


  @Getter
  @Setter
  public static class EducationDetailDto {

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

    @DecimalMin(
        value = "0.0",
        message = "Percentage cannot be negative"
    )
    @DecimalMax(
        value = "100.0",
        message = "Percentage must not exceed 100"
    )
    private Float percentage;
  }


  @Getter
  @Setter
  public static class CertificationDetailDto {

    @NotBlank(message = "Certificate name is required")
    @Size(max = 150)
    private String certificateName;

    @NotBlank(message = "Issuing organization is required")
    @Size(max = 150)
    private String issuingOrganization;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issuedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @Size(max = 100)
    private String credentialId;

    @Size(max = 500)
    private String credentialUrl;
  }
}