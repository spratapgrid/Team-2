package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.repository;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalCandidateRepository extends JpaRepository<ExternalCandidate, Long> {

    Optional<ExternalCandidate> findByEmailHashOrPhoneHash(
            String emailHash,
            String phoneHash
    );

    Optional<ExternalCandidate> findByCandidateIdAndIsDeletedFalse(Long candidateId);
}