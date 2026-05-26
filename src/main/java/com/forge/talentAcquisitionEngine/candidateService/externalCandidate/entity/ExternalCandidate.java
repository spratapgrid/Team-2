package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.enums.Source;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "external_candidate",
        uniqueConstraints = {
                @UniqueConstraint(name = "candidate_email", columnNames = "email"),
                @UniqueConstraint(name = "candidate_phone", columnNames = "phone_number"),
                @UniqueConstraint(name = "candidate_email_hash", columnNames = "email_hash"),
                @UniqueConstraint(name = "candidate_phone_hash", columnNames = "phone_hash")
        }
)
@Getter
@Setter
public class ExternalCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    @NotBlank(message = "First name is required")
    @Size(min = 1, message = "First name must be minimum 1 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "First name must contain only letters and spaces")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, message = "Last name must be minimum 1 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Last name must contain only letters and spaces")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must be a valid 10 digit Indian mobile number")
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    @Size(max = 20, message = "Gender must not exceed 20 characters")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @DecimalMin(value = "0.0", message = "Total experience cannot be negative")
    @Column(name = "total_experience_years")
    private Float totalExperienceYears;

    @DecimalMin(value = "0.0", message = "Total gap years cannot be negative")
    @Column(name = "total_gap_years")
    private Float totalGapYears;

    @NotEmpty(message = "At least one skill is required")
    @ElementCollection
    @CollectionTable(
            name = "candidate_skills",
            joinColumns = @JoinColumn(name = "candidate_id")
    )
    @Column(name = "skill", nullable = false)
    private List<
            @NotBlank(message = "Skill cannot be blank")
                    String
            > skills;

    @Size(max = 100, message = "Company name must not exceed 100 characters")
    @Column(name = "company_name")
    private String companyName;

    @Size(max = 100, message = "Designation must not exceed 100 characters")
    @Column(name = "designation")
    private String designation;

    @DecimalMin(value = "0.0", message = "Current CTC cannot be negative")
    @Column(name = "current_ctc")
    private Long currentCtc;

    @DecimalMin(value = "0.0", message = "Expected CTC cannot be negative")
    @Column(name = "expected_ctc")
    private Long expectedCtc;

    @Min(value = 0, message = "Notice period cannot be negative")
    @Column(name = "notice_period_days")
    private Integer noticePeriodDays;

    @Column(name = "willing_to_relocate")
    private Boolean willingToRelocate;

    @Size(max = 1000, message = "Free notes must not exceed 1000 characters")
    @Column(name = "free_notes", columnDefinition = "TEXT")
    private String freeNotes;

    @NotNull(message = "Source is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private Source source;

    @Valid
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "education_details", columnDefinition = "jsonb")
    private List<EducationDetail> educationDetails;

    @Valid
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "certification_details", columnDefinition = "jsonb")
    private List<CertificationDetail> certificationDetails;

    @Size(min = 64, max = 64, message = "Email hash must be 64 characters")
    @Column(name = "email_hash", unique = true,nullable = false)
    private String emailHash;

    @Size(min = 64, max = 64, message = "Phone hash must be 64 characters")
    @Column(name = "phone_hash", unique = true,nullable = false)
    private String phoneHash;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Size(max = 100, message = "Deleted by must not exceed 100 characters")
    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Size(max = 500, message = "Delete reason must not exceed 500 characters")
    @Column(name = "delete_reason", columnDefinition = "TEXT")
    private String deleteReason;

    @NotNull
    @Column(name = "pii_anonymized", nullable = false)
    private Boolean piiAnonymized = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "pii_anonymized_at")
    private LocalDateTime piiAnonymizedAt;

    @NotNull
    @Column(name = "gdpr_delete_requested", nullable = false)
    private Boolean gdprDeleteRequested = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "gdpr_delete_requested_at")
    private LocalDateTime gdprDeleteRequestedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "gdpr_delete_due_at")
    private LocalDateTime gdprDeleteDueAt;

    @NotNull
    @Column(name = "blocked_from_reapply", nullable = false)
    private Boolean blockedFromReapply = false;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Getter
    @Setter
    public static class EducationDetail {

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


    @Getter
    @Setter
    public static class CertificationDetail {

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