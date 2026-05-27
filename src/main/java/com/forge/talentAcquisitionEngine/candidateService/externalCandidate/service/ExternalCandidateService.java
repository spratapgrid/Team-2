package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.service;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.CandidateResponse;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
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

    public ExternalCandidateService(ExternalCandidateRepository externalCandidateRepository, HashUtil hashUtil) {
        this.externalCandidateRepository = externalCandidateRepository;
        this.hashUtil = hashUtil;
    }

    @Transactional
    public CandidateResponse createCandidate(ExternalCandidate candidate) {

        String email = candidate.getEmail().trim().toLowerCase();
        String phone = candidate.getPhoneNumber().trim();

        String emailHash = hashUtil.sha256(email);
        String phoneHash = hashUtil.sha256(phone);

        Optional<ExternalCandidate> existingCandidate =
                externalCandidateRepository.findByEmailHashOrPhoneHash(
                        emailHash,
                        phoneHash
                );

        if (existingCandidate.isPresent()) {

            ExternalCandidate existing = existingCandidate.get();

            if (Boolean.FALSE.equals(existing.getIsDeleted())) {
                return CandidateResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message("Already candidate exists with same email or phone number")
                        .duplicate(true)
                        .build();
            }
        }

        candidate.setEmail(email);
        candidate.setPhoneNumber(phone);
        candidate.setEmailHash(emailHash);
        candidate.setPhoneHash(phoneHash);
        candidate.setIsDeleted(false);
        candidate.setPiiAnonymized(false);
        candidate.setGdprDeleteRequested(false);

        externalCandidateRepository.save(candidate);

        return CandidateResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Candidate created successfully")
                .duplicate(false)
                .build();
    }

    public CandidateResponse updateCandidate(Long candidateId,ExternalCandidate candidate){

        ExternalCandidate existingCandidate = externalCandidateRepository.findByCandidateIdAndIsDeletedFalse(candidateId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,"Candidate not found"));

        // Update the candidate fields
        existingCandidate.setFirstName(candidate.getFirstName());
        existingCandidate.setLastName(candidate.getLastName());
        existingCandidate.setDateOfBirth(candidate.getDateOfBirth());
        existingCandidate.setGender(candidate.getGender());
        existingCandidate.setAddress(candidate.getAddress());
        existingCandidate.setTotalExperienceYears(candidate.getTotalExperienceYears());
        existingCandidate.setTotalGapYears(candidate.getTotalGapYears());
        existingCandidate.getSkill().addAll(candidate.getSkill());
        existingCandidate.setCompanyName(candidate.getCompanyName());
        existingCandidate.setDesignation(candidate.getDesignation());
        existingCandidate.setCurrentCtc(candidate.getCurrentCtc());
        existingCandidate.setExpectedCtc(candidate.getExpectedCtc());
        existingCandidate.setNoticePeriodDays(candidate.getNoticePeriodDays());
        existingCandidate.setWillingToRelocate(candidate.getWillingToRelocate());
        existingCandidate.setFreeNotes(candidate.getFreeNotes());
        existingCandidate.setEducationDetails(candidate.getEducationDetails());
        existingCandidate.setCertificationDetails(candidate.getCertificationDetails());

        externalCandidateRepository.save(existingCandidate);

        return CandidateResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Candidate updated successfully")
                .duplicate(false)
                .build();
    }

    public ExternalCandidate getByCandidateId(Long candidateId) {
        return externalCandidateRepository.findByCandidateIdAndIsDeletedFalse(candidateId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

    public void deleteById(Long candidateId) {

        ExternalCandidate existingCandidate =
                externalCandidateRepository.findByCandidateIdAndIsDeletedFalse(candidateId)
                        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Candidate not found"));
        existingCandidate.setIsDeleted(true);
        existingCandidate.setDeletedAt(LocalDateTime.now());

        externalCandidateRepository.save(existingCandidate);
    }
}