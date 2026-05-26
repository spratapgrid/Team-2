package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.repository;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalCandidateRepository extends JpaRepository<ExternalCandidate, Long> {

    Optional<ExternalCandidate> findByEmailHashOrPhoneHash(
            String emailHash,
            String phoneHash
    );

    Optional<ExternalCandidate> findByCandidateIdAndIsDeletedFalse(Long candidateId);
}