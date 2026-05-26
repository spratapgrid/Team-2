package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.service;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.CandidateResponse;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.repository.ExternalCandidateRepository;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.utility.HashUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalCandidateService {

    private final ExternalCandidateRepository externalCandidateRepository;
    private final HashUtil hashUtil;

    public ExternalCandidateService(ExternalCandidateRepository externalCandidateRepository, HashUtil hashUtil) {
        this.externalCandidateRepository = externalCandidateRepository;
        this.hashUtil = hashUtil;
    }

    public CandidateResponse createCandidate(ExternalCandidate candidate) {

        String email = candidate.getEmail() == null ? null : candidate.getEmail().trim().toLowerCase();
        String phone = candidate.getPhoneNumber() == null ? null : candidate.getPhoneNumber().trim();

        String emailHash = hashUtil.sha256(email);
        String phoneHash = hashUtil.sha256(phone);

        Optional<ExternalCandidate> existingCandidate =
                externalCandidateRepository.findByEmailHashOrPhoneHash(
                        emailHash,
                        phoneHash
                );

        // Duplicate Candidate
        if (existingCandidate.isPresent()) {

            return CandidateResponse.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message("Duplicate candidate found")
                    .duplicate(true)
                    .build();
        }
        candidate.setEmail(email);
        candidate.setPhoneNumber(phone);
        candidate.setEmailHash(emailHash);
        candidate.setPhoneHash(phoneHash);

        externalCandidateRepository.save(candidate);

        return CandidateResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Candidate created successfully")
                .duplicate(false)
                .build();

    }
}
