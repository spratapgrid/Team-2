package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.mapper;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.ExternalCandidateDto;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;

import java.util.List;
import java.util.stream.Collectors;

public class ExternalCandidateMapper {

  private ExternalCandidateMapper() {
  }

  public static ExternalCandidate dtoToEntity(
      ExternalCandidateDto dto
  ) {

    if (dto == null) {
      return null;
    }

    ExternalCandidate entity =
        new ExternalCandidate();

    entity.setCandidateId(
        dto.getCandidateId()
    );

    entity.setFirstName(
        dto.getFirstName()
    );

    entity.setLastName(
        dto.getLastName()
    );

    entity.setEmail(
        dto.getEmail()
    );

    entity.setPhoneNumber(
        dto.getPhoneNumber()
    );

    entity.setDateOfBirth(
        dto.getDateOfBirth()
    );

    entity.setGender(
        dto.getGender()
    );

    entity.setAddress(
        dto.getAddress()
    );

    entity.setTotalExperienceYears(
        dto.getTotalExperienceYears()
    );

    entity.setTotalGapYears(
        dto.getTotalGapYears()
    );

    entity.setSkills(
        dto.getSkills()
    );

    entity.setCompanyName(
        dto.getCompanyName()
    );

    entity.setDesignation(
        dto.getDesignation()
    );

    entity.setCurrentCtc(
        dto.getCurrentCtc()
    );

    entity.setExpectedCtc(
        dto.getExpectedCtc()
    );

    entity.setNoticePeriodDays(
        dto.getNoticePeriodDays()
    );

    entity.setWillingToRelocate(
        dto.getWillingToRelocate()
    );

    entity.setFreeNotes(
        dto.getFreeNotes()
    );

    entity.setSource(
        dto.getSource()
    );

    if (dto.getEducationDetails() != null) {

      List<ExternalCandidate.EducationDetail>
          educationDetails =
          dto.getEducationDetails()
              .stream()
              .map(
                  ExternalCandidateMapper::
                      educationDtoToEntity
              )
              .collect(Collectors.toList());

      entity.setEducationDetails(
          educationDetails
      );
    }

    if (dto.getCertificationDetails() != null) {

      List<ExternalCandidate.CertificationDetail>
          certificationDetails =
          dto.getCertificationDetails()
              .stream()
              .map(
                  ExternalCandidateMapper::
                      certificationDtoToEntity
              )
              .collect(Collectors.toList());

      entity.setCertificationDetails(
          certificationDetails
      );
    }

    entity.setEmailHash(
        dto.getEmailHash()
    );

    entity.setPhoneHash(
        dto.getPhoneHash()
    );

    entity.setCreatedAt(
        dto.getCreatedAt()
    );

    entity.setIsDeleted(
        dto.getIsDeleted()
    );

    entity.setDeletedAt(
        dto.getDeletedAt()
    );

    entity.setDeletedBy(
        dto.getDeletedBy()
    );

    entity.setDeleteReason(
        dto.getDeleteReason()
    );

    entity.setPiiAnonymized(
        dto.getPiiAnonymized()
    );

    entity.setPiiAnonymizedAt(
        dto.getPiiAnonymizedAt()
    );

    entity.setGdprDeleteRequested(
        dto.getGdprDeleteRequested()
    );

    entity.setGdprDeleteRequestedAt(
        dto.getGdprDeleteRequestedAt()
    );

    entity.setGdprDeleteDueAt(
        dto.getGdprDeleteDueAt()
    );

    entity.setBlockedFromReapply(
        dto.getBlockedFromReapply()
    );

    entity.setUpdatedAt(
        dto.getUpdatedAt()
    );

    return entity;
  }

  public static ExternalCandidateDto entityToDto(
      ExternalCandidate entity
  ) {

    if (entity == null) {
      return null;
    }

    ExternalCandidateDto dto =
        new ExternalCandidateDto();

    dto.setCandidateId(
        entity.getCandidateId()
    );

    dto.setFirstName(
        entity.getFirstName()
    );

    dto.setLastName(
        entity.getLastName()
    );

    dto.setEmail(
        entity.getEmail()
    );

    dto.setPhoneNumber(
        entity.getPhoneNumber()
    );

    dto.setDateOfBirth(
        entity.getDateOfBirth()
    );

    dto.setGender(
        entity.getGender()
    );

    dto.setAddress(
        entity.getAddress()
    );

    dto.setTotalExperienceYears(
        entity.getTotalExperienceYears()
    );

    dto.setTotalGapYears(
        entity.getTotalGapYears()
    );

    dto.setSkills(
        entity.getSkills()
    );

    dto.setCompanyName(
        entity.getCompanyName()
    );

    dto.setDesignation(
        entity.getDesignation()
    );

    dto.setCurrentCtc(
        entity.getCurrentCtc()
    );

    dto.setExpectedCtc(
        entity.getExpectedCtc()
    );

    dto.setNoticePeriodDays(
        entity.getNoticePeriodDays()
    );

    dto.setWillingToRelocate(
        entity.getWillingToRelocate()
    );

    dto.setFreeNotes(
        entity.getFreeNotes()
    );

    dto.setSource(
        entity.getSource()
    );

    if (entity.getEducationDetails() != null) {

      List<ExternalCandidateDto.EducationDetailDto>
          educationDetailDtos =
          entity.getEducationDetails()
              .stream()
              .map(
                  ExternalCandidateMapper::
                      educationEntityToDto
              )
              .collect(Collectors.toList());

      dto.setEducationDetails(
          educationDetailDtos
      );
    }

    if (entity.getCertificationDetails() != null) {

      List<ExternalCandidateDto.CertificationDetailDto>
          certificationDetailDtos =
          entity.getCertificationDetails()
              .stream()
              .map(
                  ExternalCandidateMapper::
                      certificationEntityToDto
              )
              .collect(Collectors.toList());

      dto.setCertificationDetails(
          certificationDetailDtos
      );
    }

    dto.setEmailHash(
        entity.getEmailHash()
    );

    dto.setPhoneHash(
        entity.getPhoneHash()
    );

    dto.setCreatedAt(
        entity.getCreatedAt()
    );

    dto.setIsDeleted(
        entity.getIsDeleted()
    );

    dto.setDeletedAt(
        entity.getDeletedAt()
    );

    dto.setDeletedBy(
        entity.getDeletedBy()
    );

    dto.setDeleteReason(
        entity.getDeleteReason()
    );

    dto.setPiiAnonymized(
        entity.getPiiAnonymized()
    );

    dto.setPiiAnonymizedAt(
        entity.getPiiAnonymizedAt()
    );

    dto.setGdprDeleteRequested(
        entity.getGdprDeleteRequested()
    );

    dto.setGdprDeleteRequestedAt(
        entity.getGdprDeleteRequestedAt()
    );

    dto.setGdprDeleteDueAt(
        entity.getGdprDeleteDueAt()
    );

    dto.setBlockedFromReapply(
        entity.getBlockedFromReapply()
    );

    dto.setUpdatedAt(
        entity.getUpdatedAt()
    );

    return dto;
  }


  private static ExternalCandidate.EducationDetail
  educationDtoToEntity(
      ExternalCandidateDto.EducationDetailDto dto
  ) {

    if (dto == null) {
      return null;
    }

    ExternalCandidate.EducationDetail entity =
        new ExternalCandidate.EducationDetail();

    entity.setDegree(
        dto.getDegree()
    );

    entity.setSpecialization(
        dto.getSpecialization()
    );

    entity.setInstitutionName(
        dto.getInstitutionName()
    );

    entity.setStartYear(
        dto.getStartYear()
    );

    entity.setEndYear(
        dto.getEndYear()
    );

    entity.setPercentage(
        dto.getPercentage()
    );

    return entity;
  }


  private static ExternalCandidateDto.EducationDetailDto
  educationEntityToDto(
      ExternalCandidate.EducationDetail entity
  ) {

    if (entity == null) {
      return null;
    }

    ExternalCandidateDto.EducationDetailDto dto =
        new ExternalCandidateDto.EducationDetailDto();

    dto.setDegree(
        entity.getDegree()
    );

    dto.setSpecialization(
        entity.getSpecialization()
    );

    dto.setInstitutionName(
        entity.getInstitutionName()
    );

    dto.setStartYear(
        entity.getStartYear()
    );

    dto.setEndYear(
        entity.getEndYear()
    );

    dto.setPercentage(
        entity.getPercentage()
    );

    return dto;
  }


  private static ExternalCandidate.CertificationDetail
  certificationDtoToEntity(
      ExternalCandidateDto.CertificationDetailDto dto
  ) {

    if (dto == null) {
      return null;
    }

    ExternalCandidate.CertificationDetail entity =
        new ExternalCandidate.CertificationDetail();

    entity.setCertificateName(
        dto.getCertificateName()
    );

    entity.setIssuingOrganization(
        dto.getIssuingOrganization()
    );

    entity.setIssuedDate(
        dto.getIssuedDate()
    );

    entity.setExpiryDate(
        dto.getExpiryDate()
    );

    entity.setCredentialId(
        dto.getCredentialId()
    );

    entity.setCredentialUrl(
        dto.getCredentialUrl()
    );

    return entity;
  }


  private static ExternalCandidateDto.CertificationDetailDto
  certificationEntityToDto(
      ExternalCandidate.CertificationDetail entity
  ) {

    if (entity == null) {
      return null;
    }

    ExternalCandidateDto.CertificationDetailDto dto =
        new ExternalCandidateDto.CertificationDetailDto();

    dto.setCertificateName(
        entity.getCertificateName()
    );

    dto.setIssuingOrganization(
        entity.getIssuingOrganization()
    );

    dto.setIssuedDate(
        entity.getIssuedDate()
    );

    dto.setExpiryDate(
        entity.getExpiryDate()
    );

    dto.setCredentialId(
        entity.getCredentialId()
    );

    dto.setCredentialUrl(
        entity.getCredentialUrl()
    );

    return dto;
  }
}