package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.service;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.CandidateResponse;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.ExternalCandidateDto;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.mapper.ExternalCandidateMapper;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.repository.ExternalCandidateRepository;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.utility.HashUtil;
import com.forge.talentAcquisitionEngine.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExternalCandidateService {

    private final ExternalCandidateRepository externalCandidateRepository;
    private final HashUtil hashUtil;

    public ExternalCandidateService(
            ExternalCandidateRepository externalCandidateRepository,
            HashUtil hashUtil
    ) {
        this.externalCandidateRepository = externalCandidateRepository;
        this.hashUtil = hashUtil;
    }

    @Transactional
    public CandidateResponse createCandidate(ExternalCandidateDto dto) {

        dto.setCandidateId(null);

        String email = normalizeEmail(dto.getEmail());
        String phone = normalizePhone(dto.getPhoneNumber());

        String emailHash = hashUtil.sha256(email);
        String phoneHash = hashUtil.sha256(phone);

        Optional<ExternalCandidate> existingCandidate =
                externalCandidateRepository.findActiveDuplicate(emailHash, phoneHash);

        if (existingCandidate.isPresent()) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    "Candidate already exists with same email or phone number"
            );
        }

        ExternalCandidate candidate = ExternalCandidateMapper.dtoToEntity(dto);

        candidate.setCandidateId(null);
        candidate.setEmail(email);
        candidate.setPhoneNumber(phone);
        candidate.setEmailHash(emailHash);
        candidate.setPhoneHash(phoneHash);

        candidate.setIsDeleted(false);
        candidate.setDeletedAt(null);
        candidate.setDeletedBy(null);
        candidate.setDeleteReason(null);

        candidate.setPiiAnonymized(false);
        candidate.setPiiAnonymizedAt(null);

        candidate.setGdprDeleteRequested(false);
        candidate.setGdprDeleteRequestedAt(null);
        candidate.setGdprDeleteDueAt(null);

        externalCandidateRepository.save(candidate);

        return CandidateResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Candidate created successfully")
                .duplicate(false)
                .build();
    }

    @Transactional
    public CandidateResponse updateCandidate(
            Long candidateId,
            ExternalCandidateDto dto
    ) {

        ExternalCandidate existingCandidate =
                externalCandidateRepository.findWithDetailsByCandidateIdAndIsDeletedFalse(candidateId)
                        .orElseThrow(() -> new BusinessException(
                                HttpStatus.NOT_FOUND,
                                "Candidate not found"
                        ));

        dto.setCandidateId(candidateId);

        String email = normalizeEmail(dto.getEmail());
        String phone = normalizePhone(dto.getPhoneNumber());

        String emailHash = hashUtil.sha256(email);
        String phoneHash = hashUtil.sha256(phone);

        Optional<ExternalCandidate> duplicateCandidate =
                externalCandidateRepository.findDuplicateForUpdate(
                        candidateId,
                        emailHash,
                        phoneHash
                );

        if (duplicateCandidate.isPresent()) {
            throw new BusinessException(
                    HttpStatus.CONFLICT,
                    "Candidate already exists with same email or phone number"
            );
        }

        ExternalCandidate updatedCandidate =
                ExternalCandidateMapper.dtoToEntity(dto);

        existingCandidate.setFirstName(updatedCandidate.getFirstName());
        existingCandidate.setLastName(updatedCandidate.getLastName());
        existingCandidate.setEmail(email);
        existingCandidate.setPhoneNumber(phone);
        existingCandidate.setEmailHash(emailHash);
        existingCandidate.setPhoneHash(phoneHash);
        existingCandidate.setDateOfBirth(updatedCandidate.getDateOfBirth());
        existingCandidate.setGender(updatedCandidate.getGender());
        existingCandidate.setAddress(updatedCandidate.getAddress());
        existingCandidate.setTotalExperienceYears(updatedCandidate.getTotalExperienceYears());
        existingCandidate.setTotalGapYears(updatedCandidate.getTotalGapYears());
        existingCandidate.setCompanyName(updatedCandidate.getCompanyName());
        existingCandidate.setDesignation(updatedCandidate.getDesignation());
        existingCandidate.setCurrentCtc(updatedCandidate.getCurrentCtc());
        existingCandidate.setExpectedCtc(updatedCandidate.getExpectedCtc());
        existingCandidate.setNoticePeriodDays(updatedCandidate.getNoticePeriodDays());
        existingCandidate.setWillingToRelocate(updatedCandidate.getWillingToRelocate());
        existingCandidate.setFreeNotes(updatedCandidate.getFreeNotes());
        existingCandidate.setSource(updatedCandidate.getSource());

        existingCandidate.getSkills().clear();
        if (updatedCandidate.getSkills() != null) {
            updatedCandidate.getSkills().forEach(skill -> {
                skill.setCandidate(existingCandidate);
                existingCandidate.getSkills().add(skill);
            });
        }

        existingCandidate.getEducationDetails().clear();
        if (updatedCandidate.getEducationDetails() != null) {
            updatedCandidate.getEducationDetails().forEach(education -> {
                education.setCandidate(existingCandidate);
                existingCandidate.getEducationDetails().add(education);
            });
        }

        existingCandidate.getCertificationDetails().clear();
        if (updatedCandidate.getCertificationDetails() != null) {
            updatedCandidate.getCertificationDetails().forEach(certification -> {
                certification.setCandidate(existingCandidate);
                existingCandidate.getCertificationDetails().add(certification);
            });
        }

        externalCandidateRepository.save(existingCandidate);

        return CandidateResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Candidate updated successfully")
                .duplicate(false)
                .build();
    }

    @Transactional(readOnly = true)
    public ExternalCandidateDto getByCandidateId(Long candidateId) {

        ExternalCandidate candidate =
                externalCandidateRepository.findWithDetailsByCandidateIdAndIsDeletedFalse(candidateId)
                        .orElseThrow(() -> new BusinessException(
                                HttpStatus.NOT_FOUND,
                                "Candidate not found"
                        ));

        return ExternalCandidateMapper.entityToDto(candidate);
    }

    @Transactional
    public void deleteById(Long candidateId) {

        ExternalCandidate candidate =
                externalCandidateRepository.findByCandidateIdAndIsDeletedFalse(candidateId)
                        .orElseThrow(() -> new BusinessException(
                                HttpStatus.NOT_FOUND,
                                "Candidate not found"
                        ));

        candidate.setIsDeleted(true);
        candidate.setDeletedAt(LocalDateTime.now());

        externalCandidateRepository.save(candidate);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizePhone(String phone) {
        return phone.trim();
    }
}