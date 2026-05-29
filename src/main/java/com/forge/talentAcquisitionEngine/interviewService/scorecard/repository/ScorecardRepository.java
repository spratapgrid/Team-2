package com.forge.talentAcquisitionEngine.interviewService.scorecard.repository;

import com.forge.talentAcquisitionEngine.interviewService.scorecard.entity.Scorecard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {

    List<Scorecard> findAllByInterview_Id(Long interviewId);

    List<Scorecard> findAllByApplication_ApplicationId(String applicationId);

    // used to enforce the unique constraint at service level before saving
    Optional<Scorecard> findByInterview_IdAndInterviewerId(Long interviewId, Long interviewerId);

    boolean existsByInterview_IdAndInterviewerId(Long interviewId, Long interviewerId);
}